package com.ikigai;

import java.util.List;
import java.util.Map;

/**
 * UserProfile — Holds everything the user tells us across all 16 questions.
 *
 * Per Ikigai circle (×4):
 *   - tags    : collected from MCQ Q1 (pick-2) + MCQ Q2 (single) = up to 3 tags
 *   - sliders : collected from Q3 sliders (3 sliders, each rated 1–5)
 *
 * Extra dimensions (×4):
 *   - workStyle    : 1 (solo) → 5 (team)
 *   - riskAppetite : 1 (stable) → 5 (adventurous)
 *   - personality  : 1 (introvert) → 5 (extrovert)
 *   - academic     : "science" | "commerce" | "arts" | "mixed" | "any"
 */
public class UserProfile {

    // Ikigai axis tags  (from MCQ questions)
    private List<String> loveTags;
    private List<String> goodTags;
    private List<String> needTags;
    private List<String> paidTags;

    // Ikigai axis sliders  (from slider questions, keys match career trait keys)
    // loveSliders  keys : creative | technical | people
    // goodSliders  keys : analytical | creative | communication
    // needSliders  keys : technology | human | environment
    // paidSliders  keys : salary | security | flexibility
    private Map<String, Integer> loveSliders;
    private Map<String, Integer> goodSliders;
    private Map<String, Integer> needSliders;
    private Map<String, Integer> paidSliders;

    // Extra dimensions
    private int workStyle;      // 1–5
    private int riskAppetite;   // 1–5
    private int personality;    // 1–5
    private String academic;    // science | commerce | arts | mixed | any

    public UserProfile(List<String> loveTags, List<String> goodTags,
                       List<String> needTags, List<String> paidTags,
                       Map<String, Integer> loveSliders, Map<String, Integer> goodSliders,
                       Map<String, Integer> needSliders, Map<String, Integer> paidSliders,
                       int workStyle, int riskAppetite, int personality, String academic) {
        this.loveTags = loveTags; this.goodTags = goodTags;
        this.needTags = needTags; this.paidTags = paidTags;
        this.loveSliders = loveSliders; this.goodSliders = goodSliders;
        this.needSliders = needSliders; this.paidSliders = paidSliders;
        this.workStyle = workStyle; this.riskAppetite = riskAppetite;
        this.personality = personality; this.academic = academic;
    }

    // Getters — Tags
    public List<String> getLoveTags() { return loveTags; }
    public List<String> getGoodTags() { return goodTags; }
    public List<String> getNeedTags() { return needTags; }
    public List<String> getPaidTags() { return paidTags; }

    // Getters — Sliders
    public Map<String, Integer> getLoveSliders() { return loveSliders; }
    public Map<String, Integer> getGoodSliders() { return goodSliders; }
    public Map<String, Integer> getNeedSliders() { return needSliders; }
    public Map<String, Integer> getPaidSliders() { return paidSliders; }

    // Getters — Extra Dimensions
    public int getWorkStyle()    { return workStyle; }
    public int getRiskAppetite() { return riskAppetite; }
    public int getPersonality()  { return personality; }
    public String getAcademic()  { return academic; }
}
