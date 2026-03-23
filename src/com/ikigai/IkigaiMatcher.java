package com.ikigai;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * IkigaiMatcher — The Core Scoring Engine.
 *
 * ┌─────────────────────────────────────────────────────────────┐
 * │  SCORING BREAKDOWN                                           │
 * │                                                              │
 * │  Per Ikigai axis (Love / Good / Need / Paid):               │
 * │    Tag match  : +10 pts per matching tag (max 20)           │
 * │    Slider fit : proximity score averaged → 0–5 pts          │
 * │    Axis total : 0–25 pts                                    │
 * │                                                              │
 * │  Compatibility axis (extra dimensions):                      │
 * │    Work style proximity   : 0–5 pts                        │
 * │    Risk appetite proximity: 0–5 pts                        │
 * │    Personality proximity  : 0–5 pts                        │
 * │    Academic background    : 5 pts (match) / 2 pts (close)  │
 * │    Compat total           : 0–20 pts                       │
 * │                                                              │
 * │  Grand total : 0–120 pts → percentage = total / 120 × 100  │
 * └─────────────────────────────────────────────────────────────┘
 */
public class IkigaiMatcher {

    private static final int MAX_AXIS_SCORE  = 25;
    private static final int MAX_COMPAT      = 20;
    private static final int GRAND_MAX       = 120; // 4×25 + 20
    private static final int TAG_POINTS      = 10;  // per matching tag
    private static final int MAX_TAG_SCORE   = 20;  // cap: 2 tags × 10

    /**
     * Score one Ikigai axis.
     * Combines tag-based matching (0–20) with slider proximity (0–5).
     */
    private int scoreAxis(List<String> userTags, List<String> careerTags,
                          Map<String, Integer> userSliders, Map<String, Integer> careerTraits) {

        // ── Tag matching ──
        int tagScore = 0;
        if (userTags != null) {
            for (String tag : userTags) {
                if (careerTags.contains(tag)) tagScore += TAG_POINTS;
            }
        }
        tagScore = Math.min(tagScore, MAX_TAG_SCORE);

        // ── Slider proximity ──
        int sliderScore = 2; // neutral default if no sliders provided
        if (userSliders != null && !userSliders.isEmpty() && careerTraits != null) {
            int total = 0;
            int count = 0;
            for (Map.Entry<String, Integer> entry : careerTraits.entrySet()) {
                Integer userVal = userSliders.get(entry.getKey());
                if (userVal != null) {
                    // Proximity: 5 pts if perfect match, decreases by 1 per step away
                    int proximity = 5 - Math.abs(userVal - entry.getValue());
                    total += Math.max(0, proximity);
                    count++;
                }
            }
            if (count > 0) sliderScore = Math.min(5, total / count);
        }

        return tagScore + sliderScore; // max 25
    }

    /**
     * Score the extra dimensions (compatibility) axis.
     * Each of 4 dimensions contributes 0–5 pts → total 0–20.
     */
    private int scoreCompatibility(UserProfile user, Career career) {
        int score = 0;

        // Work style proximity
        score += Math.max(0, 5 - Math.abs(user.getWorkStyle() - career.getIdealWorkStyle()));

        // Risk appetite proximity
        score += Math.max(0, 5 - Math.abs(user.getRiskAppetite() - career.getIdealRisk()));

        // Personality proximity
        score += Math.max(0, 5 - Math.abs(user.getPersonality() - career.getIdealPersonality()));

        // Academic background
        String userAcademic = user.getAcademic() != null ? user.getAcademic().toLowerCase() : "any";
        List<String> suitable = career.getSuitableAcademic();
        if (suitable.contains(userAcademic) || suitable.contains("any") || userAcademic.equals("any")) {
            score += 5; // perfect match
        } else if (userAcademic.equals("mixed")) {
            score += 3; // mixed background is broadly compatible
        } else {
            score += 2; // partial credit — background can be developed
        }

        return Math.min(score, MAX_COMPAT); // cap at 20
    }

    /**
     * Run matching for all careers and return sorted list (highest score first).
     */
    public List<Career> matchCareers(UserProfile user) {
        List<Career> careers = CareerDatabase.getAllCareers();

        for (Career career : careers) {
            int love  = scoreAxis(user.getLoveTags(), career.getLoveTags(), user.getLoveSliders(), career.getLoveTraits());
            int good  = scoreAxis(user.getGoodTags(), career.getGoodTags(), user.getGoodSliders(), career.getGoodTraits());
            int need  = scoreAxis(user.getNeedTags(), career.getNeedTags(), user.getNeedSliders(), career.getNeedTraits());
            int paid  = scoreAxis(user.getPaidTags(), career.getPaidTags(), user.getPaidSliders(), career.getPaidTraits());
            int compat = scoreCompatibility(user, career);

            career.setLoveScore(love);
            career.setGoodScore(good);
            career.setNeedScore(need);
            career.setPaidScore(paid);
            career.setCompatibilityScore(compat);
            career.setTotalScore(love + good + need + paid + compat);
        }

        careers.sort(Comparator.comparingInt(Career::getTotalScore).reversed());
        return careers;
    }

    /**
     * Convert a raw axis score (0–25) to a percentage (0–100).
     */
    public int axisPercent(int score) {
        return Math.round((score / (float) MAX_AXIS_SCORE) * 100);
    }

    /**
     * Convert a compatibility score (0–20) to a percentage (0–100).
     */
    public int compatPercent(int score) {
        return Math.round((score / (float) MAX_COMPAT) * 100);
    }

    /**
     * Convert total score (0–120) to overall match percentage (0–100).
     */
    public int matchPercent(int totalScore) {
        return Math.round((totalScore / (float) GRAND_MAX) * 100);
    }

    /**
     * Identify the weakest Ikigai axis for the top career match.
     */
    public String findWeakAxis(Career topCareer) {
        int love   = topCareer.getLoveScore();
        int good   = topCareer.getGoodScore();
        int need   = topCareer.getNeedScore();
        int paid   = topCareer.getPaidScore();
        int compat = topCareer.getCompatibilityScore();

        // Normalize compat to same scale as axis scores for comparison
        int compatNorm = (int)(compat * 25.0 / 20.0);

        int minScore = Math.min(Math.min(love, good), Math.min(Math.min(need, paid), compatNorm));

        if (minScore == love)       return "What you Love";
        if (minScore == good)       return "What you're Good At";
        if (minScore == need)       return "What the World Needs";
        if (minScore == paid)       return "What you can be Paid For";
        return "Personal Compatibility";
    }

    /**
     * Return personalised advice for closing the weakest axis gap.
     */
    public String getGapAdvice(String weakAxis) {
        switch (weakAxis) {
            case "What you Love":
                return "You haven't fully explored your passions yet. Try new side projects, creative hobbies, or volunteer work — your Ikigai often hides in what you'd do for free.";
            case "What you're Good At":
                return "Your passion and purpose are clear, but you need to deepen your skills. Online courses, certifications, or hands-on projects can close this gap faster than you think.";
            case "What the World Needs":
                return "You're skilled and passionate, but haven't yet found your mission. Look for roles where your abilities directly solve a societal problem — that connection will transform your motivation.";
            case "What you can be Paid For":
                return "Monetisation is your gap. Research what the market actually pays for, explore adjacent high-demand roles, or consider combining your passion with business or communication skills.";
            case "Personal Compatibility":
                return "Your work style, risk tolerance, or background doesn't fully align with this path yet — but that's fixable. Consider what small, deliberate changes to your environment or habits could bridge this gap.";
            default:
                return "Keep developing all four Ikigai circles equally. Small, consistent improvements across every dimension compound into an extraordinary career.";
        }
    }
}
