package com.example.codetour;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TripSchedule implements Serializable{
    CourseManager courseManager;
    String name;
    String startDate;
    String endDate;
    Date sd;
    Date ed;

    //날짜별로 여행지역이 다를 수 있기 때문에 int로 되어있는 지역코드랑 시군구 코드를 int[]형태로 바꿔야함
    //[0 : 출발지, 1: 도착지][날짜]
    int[][] areacode;
    int[][] sigungucode;

    String contentTypeID;
    int difdays;
    int times;
    int pNum;
    int tourBudget;
    int accBudget;
    int list_pos;
    boolean save=false; // 대양: 얘는 뭐 하는 변수?
    List<Integer> food_selection;
    List<Integer> theme_selection;

    //아래의 정보는 전부 Spot 각각에 관한 정보라서, 차후 Spot 안으로 옮기는게 다루기도 편할 듯?
    int[] startTime;
    int[] endTime;
    List<String> startPoss;
    List<String> endPoss;
    List<String> startAddr;
    List<String> endAddr;
    double[][] startPosVal; //startPos[day][pos] pos=0:x, pos=1:y
    double[][] endPosVal;

    public TripSchedule(){}
    public TripSchedule(String name, String startDate, String endDate, int pNum,
                int tourBudget, int accBudget, int[] startTime, int[] endTime, List<Integer> food_selection, List<Integer> theme_selection){
        this.name=name;
        this.startDate=startDate;
        this.endDate=endDate;
        this.pNum=pNum;
        this.tourBudget=tourBudget;
        this.accBudget=accBudget;
        this.startTime=startTime;
        this.endTime=endTime;
        this.times = endTime[0]-startTime[0]; //활동 시간
        this.food_selection=food_selection;
        this.theme_selection=theme_selection;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try{
            sd=sdf.parse(startDate);
            ed=sdf.parse(endDate);
            difdays= (int) (ed.getTime()-sd.getTime())/(24*60*60*1000);
            difdays++;
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        courseList = new Course[difdays];
        this.startPoss = new ArrayList<String>();
        this.endPoss = new ArrayList<String>();
        this.startAddr = new ArrayList<String>();
        this.endAddr = new ArrayList<String>();
        this.startPosVal = new double[difdays][];
        this.endPosVal = new double[difdays][];
        for(int i=0; i<difdays; ++i){
            this.startPoss.add("");
            this.endPoss.add("");
            this.startAddr.add("");
            this.endAddr.add("");
            this.startPosVal[i] = new double[2];
            this.endPosVal[i] = new double[2];
        }
        this.areacode=new int[2][difdays];
        this.sigungucode=new int[2][difdays];
        courseManager = new CourseManager(difdays, startTime, endTime);
    }


    public void makeCourse(){

    }
    public void AddCourse(){

    }
    public void DeleteCourse(){

    }

    public String getName(){return name;}
    public String getStartDate(){return startDate;}
    public String getEndDate(){return endDate;}
    public int getList_pos(){return list_pos;}
    public void setList_pos(int list_pos)
    {
        this.save=true;
        this.list_pos=list_pos;
    }
    public void setName(String name){
        this.name=name;
    }
    //생성자는 load를 위해, toJSONObj는 save를 위해 사용.
    public TripSchedule(JSONObject obj){
//System.out.println("Loading TripSchedule...");
        try {
            courseManager = new CourseManager(obj.getJSONObject("courseManager"));
            name = obj.getString("name");
            startDate = obj.getString("startDate");
            endDate = obj.getString("endDate");

            // 대양: sd와 ed가 모두 Date형 자료형인데, 이 클래스 내에서는 이들을 다루는 메소드가 보이지 않음.
            //      및, JSONObject로부터 Date를 바로 받아올 수 있는지 확인 필요.
            //sd = ???
            //ed = ???

            contentTypeID = obj.getString("contentTypeID");
            difdays = obj.getInt("difdays");
            pNum = obj.getInt("pNum");
            tourBudget = obj.getInt("tourBudget");
            accBudget = obj.getInt("accBudget");

            save = obj.getBoolean("save");

            food_selection = new ArrayList<Integer>();
            JSONArray food_selection_tmp = obj.getJSONArray("food_selection");
            int len_food = food_selection_tmp.length();
            for(int i=0; i<len_food; ++i){
                food_selection.add(food_selection_tmp.getInt(i));
            }

            theme_selection = new ArrayList<Integer>();
            JSONArray theme_selection_tmp = obj.getJSONArray("theme_selection");
            int len_theme = theme_selection_tmp.length();
            for(int i=0; i<len_theme; ++i){
                theme_selection.add(theme_selection_tmp.getInt(i));
            }

            startTime = new int[difdays];
            JSONArray startTimeTmp = obj.getJSONArray("startTime");
            endTime = new int[difdays];
            JSONArray endTimeTmp = obj.getJSONArray("endTime");
            startPoss = new ArrayList<String>();
            JSONArray startPossTmp = obj.getJSONArray("startPoss");
            endPoss = new ArrayList<String>();
            JSONArray endPossTmp = obj.getJSONArray("endPoss");

            startAddr = new ArrayList<String>();
            JSONArray startAddrTemp = obj.getJSONArray("startAddr");
            endAddr = new ArrayList<String>();
            JSONArray endAddrTemp = obj.getJSONArray("endAddr");

            startPosVal = new double[difdays][];
            endPosVal = new double[difdays][];
            for(int i=0; i<difdays; ++i){
                startPosVal[i] = new double[2];
                endPosVal[i] = new double[2];
            }
            JSONArray startPosValTemp = obj.getJSONArray("startPosVal");
            JSONArray endPosValTemp = obj.getJSONArray("endPosVal");

            areacode = new int[2][];
            sigungucode = new int[2][];
            for(int i=0; i<2; ++i){
                areacode[i] = new int[difdays];
                sigungucode[i] = new int[difdays];
            }

            JSONArray areacodeTemp = obj.getJSONArray("areacode");
            JSONArray sigungucodeTemp = obj.getJSONArray("sigungucode");

            for(int i=0; i<difdays; ++i){
                startTime[i] = startTimeTmp.getInt(i);
                endTime[i] = endTimeTmp.getInt(i);
                startPoss.add(startPossTmp.getString(i));
                endPoss.add(endPossTmp.getString(i));

                startAddr.add(startAddrTemp.getString(i));
                endAddr.add(endAddrTemp.getString(i));

                startPosVal[i][0] = startPosValTemp.getDouble(2*i  );
                startPosVal[i][1] = startPosValTemp.getDouble(2*i+1);
                endPosVal[i][0]   =   endPosValTemp.getDouble(2*i  );
                endPosVal[i][1]   =   endPosValTemp.getDouble(2*i+1);

                areacode[0][i]    =    areacodeTemp.getInt(2*i  );
                areacode[1][i]    =    areacodeTemp.getInt(2*i+1);
                sigungucode[0][i] = sigungucodeTemp.getInt(2*i  );
                sigungucode[1][i] = sigungucodeTemp.getInt(2*i+1);
            }
//System.out.println("Complete");
        }
        catch(Exception e){ System.out.println("Error at Loading TripSchedule"); }
    }
    public JSONObject toJSONObj(){
//System.out.println("Saving TripSchedule...");
        JSONObject ret = new JSONObject();
        JSONArray food_selection_tmp = new JSONArray();
        JSONArray theme_selection_tmp = new JSONArray();
        JSONArray startTimeTmp = new JSONArray();
        JSONArray endTimeTmp = new JSONArray();
        JSONArray startPossTmp = new JSONArray();
        JSONArray endPossTmp = new JSONArray();

        JSONArray startAddrTemp = new JSONArray();
        JSONArray endAddrTemp = new JSONArray();
        JSONArray startPosValTemp = new JSONArray();
        JSONArray endPosValTemp = new JSONArray();
        JSONArray areacodeTemp = new JSONArray();
        JSONArray sigungucodeTemp = new JSONArray();
        try{
            ret.put("courseManager", courseManager.toJSONObj());
            ret.put("name", name);
            ret.put("startDate", startDate);
            ret.put("endDate", endDate);
            //sd ??
            //ed ??
            ret.put("difdays", difdays);
            ret.put("pNum", pNum);
            ret.put("tourBudget", tourBudget);
            ret.put("accBudget", accBudget);

            ret.put("save", save);

            int food_selection_len = food_selection.size();
            for(int i=0; i<food_selection_len; ++i){
                food_selection_tmp.put(food_selection.get(i));
            }
            ret.put("food_selection", food_selection_tmp);

            int theme_selection_len = theme_selection.size();
            for(int i=0; i<theme_selection_len; ++i){
                theme_selection_tmp.put(theme_selection.get(i));
            }
            ret.put("theme_selection", theme_selection_tmp);

            for(int i=0; i<difdays; ++i){
                //startTimeTmp.put(startTime[i]);
                //endTimeTmp.put(endTime[i]);
                startPossTmp.put(startPoss.get(i));
                endPossTmp.put(endPoss.get(i));

                startAddrTemp.put(startAddr.get(i));
                endAddrTemp.put(endAddr.get(i));

                startPosValTemp.put(startPosVal[i][0]);
                startPosValTemp.put(startPosVal[i][1]);
                endPosValTemp.put(endPosVal[i][0]);
                endPosValTemp.put(endPosVal[i][1]);

                areacodeTemp.put(areacode[0][i]);
                areacodeTemp.put(areacode[1][i]);
                sigungucodeTemp.put(sigungucode[0][i]);
                sigungucodeTemp.put(sigungucode[1][i]);
            }

            ret.put("startTime", startTimeTmp);
            ret.put("endTime", endTimeTmp);
            ret.put("startPoss", startPossTmp);
            ret.put("endPoss", endPossTmp);

            ret.put("startAddr", startAddrTemp);
            ret.put("endAddr", endAddrTemp);
            ret.put("startPosVal", startPosValTemp);
            ret.put("endPosVal", endPosValTemp);
            ret.put("areacode", areacodeTemp);
            ret.put("sigungucode", sigungucodeTemp);

//System.out.println("Complete");
        }
        catch(Exception e){ System.out.println("Error at Saving TripSchedule"); }
        return ret;
    }

    public List<Course> getCourseList() {
        return courseManager.getCourseList();
    }
}
