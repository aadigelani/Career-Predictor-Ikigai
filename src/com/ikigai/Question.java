package com.ikigai;

import java.util.List;

/**
 * Question — Data model for a single quiz question served via GET /questions.
 *
 * Fields:
 *   id         : unique question identifier (e.g. "love-1", "profile-numerical")
 *   section    : 1–6 (which quiz section it belongs to)
 *   sectionName: display name (e.g. "What you Love")
 *   sectionColor: CSS color key (persimmon / gold / sage / indigo / plum / coral)
 *   type       : "multi" | "single" | "slider" | "multi+slider"
 *   number     : question number (1–20)
 *   label      : small label shown above question (e.g. "Multi-select")
 *   question   : the main question text
 *   hint       : sub-hint text below the question
 *   circle     : Ikigai axis this feeds ("love" | "good" | "need" | "paid" | "profile")
 *   options    : list of MCQ options (null for slider-only questions)
 *   sliders    : list of sliders (null for MCQ-only questions)
 *   maxSelect  : max options selectable (1 for single, 2 for multi)
 */
public class Question {

    private String id;
    private int section;
    private String sectionName;
    private String sectionColor;
    private String type;
    private int number;
    private String label;
    private String question;
    private String hint;
    private String circle;
    private List<Option> options;
    private List<Slider> sliders;
    private int maxSelect;

    // ── Inner class: Option ───────────────────────────────────────────────
    public static class Option {
        private String letter;
        private String text;
        private String tag;  // Ikigai tag this option maps to

        public Option(String letter, String text, String tag) {
            this.letter = letter; this.text = text; this.tag = tag;
        }
        public String getLetter() { return letter; }
        public String getText()   { return text; }
        public String getTag()    { return tag; }
    }

    // ── Inner class: Slider ───────────────────────────────────────────────
    public static class Slider {
        private String id;        // HTML slider id (e.g. "sl-love-creative")
        private String label;     // Display label
        private String leftLabel; // Left end label
        private String rightLabel;// Right end label
        private String syncKey;   // Optional JS sync function key

        public Slider(String id, String label, String leftLabel, String rightLabel, String syncKey) {
            this.id = id; this.label = label;
            this.leftLabel = leftLabel; this.rightLabel = rightLabel;
            this.syncKey = syncKey;
        }
        public String getId()          { return id; }
        public String getLabel()       { return label; }
        public String getLeftLabel()   { return leftLabel; }
        public String getRightLabel()  { return rightLabel; }
        public String getSyncKey()     { return syncKey != null ? syncKey : ""; }
    }

    // ── Constructor ───────────────────────────────────────────────────────
    public Question(String id, int section, String sectionName, String sectionColor,
                    String type, int number, String label, String question, String hint,
                    String circle, List<Option> options, List<Slider> sliders, int maxSelect) {
        this.id = id; this.section = section;
        this.sectionName = sectionName; this.sectionColor = sectionColor;
        this.type = type; this.number = number;
        this.label = label; this.question = question;
        this.hint = hint; this.circle = circle;
        this.options = options; this.sliders = sliders;
        this.maxSelect = maxSelect;
    }

    // ── Getters ───────────────────────────────────────────────────────────
    public String getId()             { return id; }
    public int getSection()           { return section; }
    public String getSectionName()    { return sectionName; }
    public String getSectionColor()   { return sectionColor; }
    public String getType()           { return type; }
    public int getNumber()            { return number; }
    public String getLabel()          { return label; }
    public String getQuestion()       { return question; }
    public String getHint()           { return hint; }
    public String getCircle()         { return circle; }
    public List<Option> getOptions()  { return options; }
    public List<Slider> getSliders()  { return sliders; }
    public int getMaxSelect()         { return maxSelect; }
}
