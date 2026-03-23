package com.ikigai;
import com.sun.net.httpserver.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class PredictHandler implements HttpHandler {
    private final IkigaiMatcher matcher=new IkigaiMatcher();
    @Override public void handle(HttpExchange ex) throws IOException {
        ex.getResponseHeaders().add("Access-Control-Allow-Origin","*");
        ex.getResponseHeaders().add("Access-Control-Allow-Methods","GET,POST,OPTIONS");
        ex.getResponseHeaders().add("Access-Control-Allow-Headers","Content-Type");
        if("OPTIONS".equalsIgnoreCase(ex.getRequestMethod())){ex.sendResponseHeaders(204,-1);return;}
        if(!"POST".equalsIgnoreCase(ex.getRequestMethod())){send(ex,405,"{\"error\":\"POST only\"}");return;}
        String body=new String(ex.getRequestBody().readAllBytes(),StandardCharsets.UTF_8);
        UserProfile user=parse(body);
        List<Career> ranked=matcher.matchCareers(user);
        List<Career> top3=ranked.subList(0,Math.min(3,ranked.size()));
        String weak=matcher.findWeakAxis(top3.get(0));
        ex.getResponseHeaders().add("Content-Type","application/json");
        send(ex,200,buildJson(top3,weak,matcher.getGapAdvice(weak)));
    }
    private UserProfile parse(String j){
        return new UserProfile(arr(j,"loveTags"),arr(j,"goodTags"),arr(j,"needTags"),arr(j,"paidTags"),
            imap(j,"loveSliders"),imap(j,"goodSliders"),imap(j,"needSliders"),imap(j,"paidSliders"),
            ival(j,"workStyle",3),ival(j,"riskAppetite",3),ival(j,"personality",3),sval(j,"academic"));
    }
    private List<String> arr(String j,String k){
        List<String> r=new ArrayList<>();int ki=j.indexOf("\""+k+"\"");if(ki==-1)return r;
        int as=j.indexOf('[',ki),ae=j.indexOf(']',as);if(as==-1||ae==-1)return r;
        for(String i:j.substring(as+1,ae).split(",")){String cl=i.trim().replace("\"","").replace("'","");if(!cl.isEmpty())r.add(cl);}
        return r;
    }
    private Map<String,Integer> imap(String j,String k){
        Map<String,Integer> r=new HashMap<>();int ki=j.indexOf("\""+k+"\"");if(ki==-1)return r;
        int os=j.indexOf('{',ki),ac=j.indexOf('[',ki);if(ac!=-1&&ac<os)return r;
        int oe=j.indexOf('}',os);if(os==-1||oe==-1)return r;
        for(String p:j.substring(os+1,oe).split(",")){String[]kv=p.split(":");if(kv.length==2){try{r.put(kv[0].trim().replace("\"",""),Integer.parseInt(kv[1].trim()));}catch(NumberFormatException e){}}}
        return r;
    }
    private int ival(String j,String k,int def){
        int ki=j.indexOf("\""+k+"\"");if(ki==-1)return def;int ci=j.indexOf(':',ki);if(ci==-1)return def;
        int s=ci+1;while(s<j.length()&&!Character.isDigit(j.charAt(s)))s++;int e=s;while(e<j.length()&&Character.isDigit(j.charAt(e)))e++;
        try{return Integer.parseInt(j.substring(s,e));}catch(NumberFormatException ex){return def;}
    }
    private String sval(String j,String k){
        int ki=j.indexOf("\""+k+"\"");if(ki==-1)return"any";int ci=j.indexOf(':',ki);
        int q1=j.indexOf('"',ci+1),q2=j.indexOf('"',q1+1);if(q1==-1||q2==-1)return"any";
        return j.substring(q1+1,q2);
    }
    private String buildJson(List<Career> top3,String weak,String advice){
        StringBuilder sb=new StringBuilder();sb.append("{\"results\":[");
        for(int i=0;i<top3.size();i++){
            Career c=top3.get(i);
            String[]steps=c.getRoadmapSteps()!=null?c.getRoadmapSteps().split("\\|"):new String[]{};
            sb.append("{\"rank\":").append(i+1).append(",\"name\":\"").append(e(c.getName())).append("\"")
              .append(",\"description\":\"").append(e(c.getDescription())).append("\"")
              .append(",\"isNiche\":").append(c.isNiche()).append(",\"cluster\":\"").append(e(c.getCluster())).append("\"")
              .append(",\"matchPercent\":").append(matcher.matchPercent(c.getTotalScore()))
              .append(",\"axes\":{\"love\":").append(matcher.axisPercent(c.getLoveScore()))
              .append(",\"good\":").append(matcher.axisPercent(c.getGoodScore()))
              .append(",\"need\":").append(matcher.axisPercent(c.getNeedScore()))
              .append(",\"paid\":").append(matcher.axisPercent(c.getPaidScore()))
              .append(",\"compatibility\":").append(matcher.compatPercent(c.getCompatibilityScore())).append("}")
              .append(",\"roadmap\":{\"stream\":\"").append(e(c.getRoadmapStream())).append("\"")
              .append(",\"exams\":\"").append(e(c.getRoadmapExams())).append("\"")
              .append(",\"colleges\":\"").append(e(c.getRoadmapColleges())).append("\"")
              .append(",\"timeline\":\"").append(e(c.getRoadmapTimeline())).append("\"")
              .append(",\"salaryStart\":\"").append(e(c.getRoadmapSalaryStart())).append("\"")
              .append(",\"salaryGrowth\":\"").append(e(c.getRoadmapSalaryGrowth())).append("\",\"steps\":[");
            for(int s=0;s<steps.length;s++){sb.append("\"").append(e(steps[s].trim())).append("\"");if(s<steps.length-1)sb.append(",");}
            sb.append("]}");sb.append("}");if(i<top3.size()-1)sb.append(",");
        }
        sb.append("],\"weakAxis\":\"").append(e(weak)).append("\",\"advice\":\"").append(e(advice)).append("\"}");
        return sb.toString();
    }
    private String e(String s){if(s==null)return"";return s.replace("\\","\\\\").replace("\"","\\\"");}
    private void send(HttpExchange ex,int code,String body) throws IOException{
        byte[]b=body.getBytes(StandardCharsets.UTF_8);ex.sendResponseHeaders(code,b.length);
        try(OutputStream os=ex.getResponseBody()){os.write(b);}
    }
}
