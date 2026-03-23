package com.ikigai;
import java.util.*;
public class Career {
    private String name,description,cluster;
    private boolean isNiche;
    private List<String> loveTags,goodTags,needTags,paidTags;
    private Map<String,Integer> loveTraits,goodTraits,needTraits,paidTraits;
    private int idealWorkStyle,idealRisk,idealPersonality;
    private List<String> suitableAcademic;
    private String roadmapStream,roadmapExams,roadmapColleges,roadmapTimeline,roadmapSalaryStart,roadmapSalaryGrowth,roadmapSteps;
    private int loveScore,goodScore,needScore,paidScore,compatibilityScore,totalScore;
    public Career(String name,String description,boolean isNiche,String cluster,
        List<String> lT,List<String> gT,List<String> nT,List<String> pT,
        Map<String,Integer> lt,Map<String,Integer> gt,Map<String,Integer> nt,Map<String,Integer> pt,
        int ws,int risk,int pers,List<String> acad,
        String rStream,String rExams,String rColleges,String rTimeline,String rSalStart,String rSalGrow,String rSteps){
        this.name=name;this.description=description;this.isNiche=isNiche;this.cluster=cluster;
        this.loveTags=lT;this.goodTags=gT;this.needTags=nT;this.paidTags=pT;
        this.loveTraits=lt;this.goodTraits=gt;this.needTraits=nt;this.paidTraits=pt;
        this.idealWorkStyle=ws;this.idealRisk=risk;this.idealPersonality=pers;this.suitableAcademic=acad;
        this.roadmapStream=rStream;this.roadmapExams=rExams;this.roadmapColleges=rColleges;
        this.roadmapTimeline=rTimeline;this.roadmapSalaryStart=rSalStart;this.roadmapSalaryGrowth=rSalGrow;this.roadmapSteps=rSteps;
    }
    public String getName(){return name;} public String getDescription(){return description;}
    public boolean isNiche(){return isNiche;} public String getCluster(){return cluster;}
    public List<String> getLoveTags(){return loveTags;} public List<String> getGoodTags(){return goodTags;}
    public List<String> getNeedTags(){return needTags;} public List<String> getPaidTags(){return paidTags;}
    public Map<String,Integer> getLoveTraits(){return loveTraits;} public Map<String,Integer> getGoodTraits(){return goodTraits;}
    public Map<String,Integer> getNeedTraits(){return needTraits;} public Map<String,Integer> getPaidTraits(){return paidTraits;}
    public int getIdealWorkStyle(){return idealWorkStyle;} public int getIdealRisk(){return idealRisk;}
    public int getIdealPersonality(){return idealPersonality;} public List<String> getSuitableAcademic(){return suitableAcademic;}
    public String getRoadmapStream(){return roadmapStream;} public String getRoadmapExams(){return roadmapExams;}
    public String getRoadmapColleges(){return roadmapColleges;} public String getRoadmapTimeline(){return roadmapTimeline;}
    public String getRoadmapSalaryStart(){return roadmapSalaryStart;} public String getRoadmapSalaryGrowth(){return roadmapSalaryGrowth;}
    public String getRoadmapSteps(){return roadmapSteps;}
    public int getLoveScore(){return loveScore;} public int getGoodScore(){return goodScore;}
    public int getNeedScore(){return needScore;} public int getPaidScore(){return paidScore;}
    public int getCompatibilityScore(){return compatibilityScore;} public int getTotalScore(){return totalScore;}
    public void setLoveScore(int s){loveScore=s;} public void setGoodScore(int s){goodScore=s;}
    public void setNeedScore(int s){needScore=s;} public void setPaidScore(int s){paidScore=s;}
    public void setCompatibilityScore(int s){compatibilityScore=s;} public void setTotalScore(int s){totalScore=s;}
}
