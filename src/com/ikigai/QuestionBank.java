package com.ikigai;

import java.util.*;

/**
 * QuestionBank — Single source of truth for all 20 quiz questions.
 *
 * To add, edit, or remove a question: change ONLY this file.
 * The frontend fetches these via GET /questions and renders them dynamically.
 *
 * Question types:
 *   "multi"        — MCQ, pick up to maxSelect options
 *   "single"       — MCQ, pick exactly 1 option
 *   "slider"       — Slider(s) only, no MCQ
 *   "multi+slider" — MCQ first, then sliders below
 *   "single+slider"— Single MCQ first, then slider below
 */
public class QuestionBank {

    // ── Shorthand helpers ─────────────────────────────────────────────────
    private static Question.Option o(String letter, String text, String tag) {
        return new Question.Option(letter, text, tag);
    }
    private static Question.Slider s(String id, String label, String left, String right, String sync) {
        return new Question.Slider(id, label, left, right, sync);
    }
    private static List<Question.Option> opts(Question.Option... items) {
        return Arrays.asList(items);
    }
    private static List<Question.Slider> sliders(Question.Slider... items) {
        return Arrays.asList(items);
    }

    public static List<Question> getAllQuestions() {
        List<Question> qs = new ArrayList<>();

        // ════════════════════════════════════════════════════════
        // SECTION 1 — What you Love  (Q1–Q3)
        // ════════════════════════════════════════════════════════

        qs.add(new Question("love-1", 1, "What you Love", "persimmon",
            "multi", 1,
            "Question 1 of 20 — Multi-select",
            "What activities make you lose track of time?",
            "Pick up to 2 that feel most true to you.",
            "love",
            opts(
                o("A", "Building, tinkering with machines or technology", "tech"),
                o("B", "Listening to, guiding, or helping other people", "people"),
                o("C", "Creating art, music, stories, or visual content", "creative"),
                o("D", "Solving complex puzzles or logical problems", "logic"),
                o("E", "Being in nature, outdoors, or physical environments", "nature"),
                o("F", "Teaching, explaining, or sharing knowledge", "teach"),
                o("G", "Leading teams, building systems, running projects", "lead"),
                o("H", "Speed, performance, motorsports, or extreme systems", "speed")
            ),
            null, 2
        ));

        qs.add(new Question("love-2", 1, "What you Love", "persimmon",
            "single", 2,
            "Question 2 of 20 — Single select",
            "Which best describes your dream workday?",
            "Choose just one.",
            "love",
            opts(
                o("A", "Solving complex technical or engineering problems", "logic"),
                o("B", "Working creatively with a talented, inspired team", "creative"),
                o("C", "Helping, mentoring, or guiding people to grow", "people"),
                o("D", "Researching, learning, discovering new things daily", "teach"),
                o("E", "Pushing systems to their absolute performance limit", "speed"),
                o("F", "Communicating ideas that move and inspire others", "lead")
            ),
            null, 1
        ));

        qs.add(new Question("love-3", 1, "What you Love", "persimmon",
            "slider", 3,
            "Question 3 of 20 — Sliders",
            "How much do these activities energise you?",
            "Rate each from 1 (drains me) to 5 (fuels me completely).",
            "love",
            null,
            sliders(
                s("sl-love-creative",  "Creative work",             "Drains me", "Fuels me", ""),
                s("sl-love-technical", "Technical problem-solving",  "Drains me", "Fuels me", ""),
                s("sl-love-people",    "Working with people",        "Drains me", "Fuels me", "")
            ),
            0
        ));

        // ════════════════════════════════════════════════════════
        // SECTION 2 — What you're Good At  (Q4–Q6)
        // ════════════════════════════════════════════════════════

        qs.add(new Question("good-1", 2, "What you're Good At", "gold",
            "multi", 4,
            "Question 4 of 20 — Multi-select",
            "What do people come to you for help with?",
            "Pick up to 2 that feel most natural to you.",
            "good",
            opts(
                o("A", "Coding, programming, or software thinking", "coding"),
                o("B", "Writing, speaking, or communication", "communication"),
                o("C", "Analysis, research, or pattern recognition", "analysis"),
                o("D", "Design, aesthetics, or visual thinking", "design"),
                o("E", "Emotional support, empathy, or counselling", "empathy"),
                o("F", "Mathematics, physics, or engineering principles", "math"),
                o("G", "Strategy, planning, or business thinking", "strategy"),
                o("H", "Hands-on building, fixing, or prototyping", "hands")
            ),
            null, 2
        ));

        qs.add(new Question("good-2", 2, "What you're Good At", "gold",
            "single", 5,
            "Question 5 of 20 — Single select",
            "Which subject or skill came most naturally to you?",
            "The one that just clicked — no real effort needed.",
            "good",
            opts(
                o("A", "Mathematics and logical reasoning", "math"),
                o("B", "Sciences — Physics, Biology, Chemistry", "analysis"),
                o("C", "Languages, writing, or literature", "communication"),
                o("D", "Art, design, or visual creation", "design"),
                o("E", "Commerce, economics, or business", "strategy"),
                o("F", "Computer Science or programming", "coding")
            ),
            null, 1
        ));

        qs.add(new Question("good-3", 2, "What you're Good At", "gold",
            "slider", 6,
            "Question 6 of 20 — Sliders",
            "Rate your confidence in these skill areas.",
            "1 = still a beginner · 5 = genuinely strong",
            "good",
            null,
            sliders(
                s("sl-good-analytical",    "Analytical thinking",  "Beginner", "Very strong", ""),
                s("sl-good-creative",      "Creative thinking",    "Beginner", "Very strong", ""),
                s("sl-good-communication", "Communication",        "Beginner", "Very strong", "")
            ),
            0
        ));

        // ════════════════════════════════════════════════════════
        // SECTION 3 — What the World Needs  (Q7–Q9)
        // ════════════════════════════════════════════════════════

        qs.add(new Question("need-1", 3, "What the World Needs", "sage",
            "multi", 7,
            "Question 7 of 20 — Multi-select",
            "What problems in the world bother you most?",
            "Pick up to 2 that move you most deeply.",
            "need",
            opts(
                o("A", "Digital threats — cybercrime, data privacy, security", "cyber"),
                o("B", "Healthcare — disease, mental health, access", "health"),
                o("C", "Education — inequality, access, quality", "education"),
                o("D", "Environment — climate change, sustainable cities", "climate"),
                o("E", "Responsible AI — bias, ethics, misuse of tech", "ai-ethics"),
                o("F", "Performance & safety — sports, automotive, aerospace", "performance"),
                o("G", "Economic growth — small business, financial inclusion", "business"),
                o("H", "Underrepresented stories — journalism, documentary", "stories")
            ),
            null, 2
        ));

        // Q8 is adaptive — placeholder served by Java, actual options built by frontend JS
        qs.add(new Question("need-2", 3, "What the World Needs", "sage",
            "single", 8,
            "Question 8 of 20 — Single select · Adapts to your Q7 answers",
            "Digging deeper — which of these feels most like your mission?",
            "Based on what you selected above, here are the most relevant options for you.",
            "need",
            opts(
                o("A", "Advance technology and push what is possible", "performance"),
                o("B", "Improve human health, wellbeing, or resilience", "health"),
                o("C", "Make quality education accessible to everyone", "education"),
                o("D", "Protect the environment and fight climate change", "climate"),
                o("E", "Make AI and data systems fairer and more ethical", "ai-ethics"),
                o("F", "Tell stories that expose injustice and shift culture", "stories")
            ),
            null, 1
        ));

        qs.add(new Question("need-3", 3, "What the World Needs", "sage",
            "slider", 9,
            "Question 9 of 20 — Sliders",
            "How much do these causes matter to you?",
            "1 = not my focus · 5 = deeply important to me",
            "need",
            null,
            sliders(
                s("sl-need-technology",  "Technology & innovation",    "Not my focus", "Deeply matters", ""),
                s("sl-need-human",       "Human wellbeing",             "Not my focus", "Deeply matters", ""),
                s("sl-need-environment", "Protecting the environment",  "Not my focus", "Deeply matters", "")
            ),
            0
        ));

        // ════════════════════════════════════════════════════════
        // SECTION 4 — What you can be Paid For  (Q10–Q12)
        // ════════════════════════════════════════════════════════

        qs.add(new Question("paid-1", 4, "What you can be Paid For", "indigo",
            "multi", 10,
            "Question 10 of 20 — Multi-select",
            "What industry do you see yourself working in?",
            "Pick up to 2.",
            "paid",
            opts(
                o("A", "Tech industry — software, hardware, product", "tech-industry"),
                o("B", "Creative industry — media, design, entertainment", "creative-industry"),
                o("C", "Research & academia — universities, labs", "research"),
                o("D", "Corporate sector — consulting, finance, strategy", "corporate"),
                o("E", "Startup / entrepreneurship — build your own thing", "startup"),
                o("F", "NGO / social impact — meaning over profit", "ngo"),
                o("G", "Highly specialised — motorsport, space, biotech", "specialised"),
                o("H", "Freelance / portfolio career — flexible & free", "freelance")
            ),
            null, 2
        ));

        qs.add(new Question("paid-2", 4, "What you can be Paid For", "indigo",
            "single", 11,
            "Question 11 of 20 — Single select",
            "What does career success look like to you?",
            "Be honest — not what sounds impressive, what actually matters to you.",
            "paid",
            opts(
                o("A", "High income — financial freedom above all", "corporate"),
                o("B", "Deep expertise — becoming the best in my niche", "research"),
                o("C", "Real impact — changing something that genuinely matters", "ngo"),
                o("D", "Building something — my own company or product", "startup"),
                o("E", "Freedom — choosing my own projects, time, and clients", "freelance"),
                o("F", "Mastery — recognised for rare technical or creative skill", "specialised")
            ),
            null, 1
        ));

        qs.add(new Question("paid-3", 4, "What you can be Paid For", "indigo",
            "slider", 12,
            "Question 12 of 20 — Sliders",
            "How important are these to you in a career?",
            "1 = barely matters · 5 = non-negotiable",
            "paid",
            null,
            sliders(
                s("sl-paid-salary",      "High salary & financial rewards", "Barely matters", "Non-negotiable", ""),
                s("sl-paid-security",    "Job security & stability",        "Barely matters", "Non-negotiable", ""),
                s("sl-paid-flexibility", "Flexibility & independence",      "Barely matters", "Non-negotiable", "")
            ),
            0
        ));

        // ════════════════════════════════════════════════════════
        // SECTION 5 — About You  (Q13–Q16)
        // ════════════════════════════════════════════════════════

        qs.add(new Question("about-1", 5, "About You", "plum",
            "slider", 13,
            "Question 13 of 20 — Slider",
            "How do you prefer to work?",
            "1 = deep solo focus · 5 = thrive in large teams",
            "profile",
            null,
            sliders(
                s("sl-workStyle", "Work style", "Solo focus", "Large team", "workStyle")
            ),
            0
        ));

        qs.add(new Question("about-2", 5, "About You", "plum",
            "slider", 14,
            "Question 14 of 20 — Slider",
            "Your relationship with risk and uncertainty?",
            "1 = stability above all · 5 = embrace uncertainty",
            "profile",
            null,
            sliders(
                s("sl-riskAppetite", "Risk appetite", "Stable & secure", "High risk/reward", "riskAppetite")
            ),
            0
        ));

        qs.add(new Question("about-3", 5, "About You", "plum",
            "slider", 15,
            "Question 15 of 20 — Slider",
            "How would you describe your social energy?",
            "1 = strong introvert · 5 = strong extrovert",
            "profile",
            null,
            sliders(
                s("sl-personality", "Personality", "Introvert", "Extrovert", "personality")
            ),
            0
        ));

        qs.add(new Question("about-4", 5, "About You", "plum",
            "single", 16,
            "Question 16 of 20 — Single select",
            "What stream are you from (or drawn to)?",
            "Choose the one that fits best.",
            "profile",
            opts(
                o("A", "Science — Physics, Math, Engineering", "science"),
                o("B", "Commerce — Business, Economics, Finance", "commerce"),
                o("C", "Arts / Humanities — Literature, Design, Media", "arts"),
                o("D", "Mixed / Interdisciplinary / Still figuring out", "mixed")
            ),
            null, 1
        ));

        // ════════════════════════════════════════════════════════
        // SECTION 6 — Your Profile  (Q17–Q20)
        // ════════════════════════════════════════════════════════

        qs.add(new Question("profile-1", 6, "Your Profile", "coral",
            "single", 17,
            "Question 17 of 20 — Personality Type",
            "Which of these sounds most like you on a typical day?",
            "Be honest — no personality type is better than another.",
            "profile",
            opts(
                o("A", "I love being around people — social energy fuels me, not drains me", "people"),
                o("B", "I do my best work alone — deep focus and quiet are my superpowers", "logic"),
                o("C", "I enjoy both — depends on the task and mood", "teach"),
                o("D", "I naturally take charge — I like organising and driving things forward", "lead"),
                o("E", "I'm curious and imaginative — I'd rather explore ideas than follow routines", "creative"),
                o("F", "I'm hands-on and practical — I'd rather build or do than think or talk", "nature")
            ),
            null, 1
        ));

        qs.add(new Question("profile-2", 6, "Your Profile", "coral",
            "single+slider", 18,
            "Question 18 of 20 — Numerical Ability",
            "How do you feel about numbers and data?",
            "Pick what feels truest, then rate your confidence.",
            "profile",
            opts(
                o("A", "I genuinely enjoy maths, equations, and data analysis", "math"),
                o("B", "I can work with numbers but prefer using them for a purpose", "analysis"),
                o("C", "I use data to make decisions but I'm not a numbers person by nature", "strategy"),
                o("D", "Numbers aren't my strong suit — I lean more towards language and creativity", "communication")
            ),
            sliders(
                s("sl-numerical", "Numerical confidence", "Not confident", "Very strong", "numerical")
            ),
            1
        ));

        qs.add(new Question("profile-3", 6, "Your Profile", "coral",
            "single+slider", 19,
            "Question 19 of 20 — Verbal Ability",
            "How comfortable are you with language, writing, and communication?",
            "Pick what resonates, then rate your confidence.",
            "profile",
            opts(
                o("A", "I love writing and speaking — words come naturally and powerfully to me", "communication"),
                o("B", "I'm good at explaining things clearly — people understand me well", "teach"),
                o("C", "I communicate well but prefer working with systems and data", "analysis"),
                o("D", "I'm more comfortable with code or numbers than with language", "coding")
            ),
            sliders(
                s("sl-verbal", "Verbal confidence", "Not confident", "Very strong", "verbal")
            ),
            1
        ));

        qs.add(new Question("profile-4", 6, "Your Profile", "coral",
            "single", 20,
            "Question 20 of 20 — Workplace Environment",
            "Which work environment would make you feel most alive?",
            "Picture a regular Tuesday — which setting sounds best?",
            "profile",
            opts(
                o("A", "A quiet lab or research space — deep focus, minimal interruptions", "research"),
                o("B", "A fast-moving tech office — whiteboards, sprints, collaborative energy", "tech-industry"),
                o("C", "A creative studio — open plan, music playing, making things together", "creative-industry"),
                o("D", "Working remotely from wherever I want — full autonomy over my space", "freelance"),
                o("E", "A specialised field setting — hospital, cockpit, factory, race track", "specialised"),
                o("F", "A corporate office — structure, hierarchy, meetings, clear career ladder", "corporate")
            ),
            null, 1
        ));

        return qs;
    }
}
