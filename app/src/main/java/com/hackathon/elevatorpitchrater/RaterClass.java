package com.hackathon.elevatorpitchrater;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by minuf on 8/27/2016.
 */
public class RaterClass {

    private String convertedSpeech;
    ArrayList<String> adjectivesList = new ArrayList<String>();
    ArrayList<String> verbsList = new ArrayList<String>();
    HashMap<String,String> raterParameters;
    ArrayList<String> nounList = new ArrayList<String>();

    public void categorizeHashMap(HashMap<String,ArrayList<String>> p)
    {
        System.out.println("This is what is to be categorized : "+p);
        for(HashMap.Entry<String,ArrayList<String>> entry : p.entrySet())
        {
            String currentWord = entry.getKey().toString();
            ArrayList<String> temp = entry.getValue();
            for(int i=0;i<temp.size();i++)
            {
                String tempType = temp.get(i);
                if(tempType.equals("adjective"))
                {
                    Log.v("CheckAdj","This is an Adjective : "+currentWord);
                    adjectivesList.add(currentWord);
                }
                if(tempType.equals("verb"))
                {
                    Log.v("CheckVerb","This is a Verb : "+currentWord);
                    verbsList.add(currentWord);
                }
                if(tempType.equals("noun"))
                {
                    Log.v("CheckNoun","This is a Noun: "+currentWord);
                    nounList.add(currentWord);
                }
            }
        }


    }
    public RaterClass(String convertedSpeech)
    {
        //this.convertedSpeech = convertedSpeech;
        raterParameters = new HashMap<String,String>();
        raterParameters.put("Greeting",null);
        raterParameters.put("Profession",null);
       // raterParameters.put("Experience",null);
        raterParameters.put("RepeatedAdjectives",null);
        raterParameters.put("RepeatedVerbs",null);
        raterParameters.put("LengthOfSpeech",null);
        raterParameters.put("Education",null);
        this.convertedSpeech = convertedSpeech;
    }

    public HashMap<String,Integer> getCountOfWords(String convertedSpeech)
    {
        ArrayList<String> wordsToBeEliminated = new ArrayList<String>();
        wordsToBeEliminated.add("is");
        wordsToBeEliminated.add("the");
        wordsToBeEliminated.add("a");
        wordsToBeEliminated.add("was");
        wordsToBeEliminated.add("then");
        wordsToBeEliminated.add("when");
        wordsToBeEliminated.add("where");
        wordsToBeEliminated.add("have");
        wordsToBeEliminated.add("has");
        wordsToBeEliminated.add("had");
        wordsToBeEliminated.add("thus");
        wordsToBeEliminated.add("and");
        HashMap<String,Integer> wordsFromConvertedSpeech = new HashMap<String,Integer>();
        convertedSpeech= convertedSpeech.toLowerCase();
        StringTokenizer st = new StringTokenizer(convertedSpeech);
        while(st.hasMoreTokens())
        {
            String temp = st.nextToken();
            if(wordsToBeEliminated.contains(temp))
            {
                continue;
            }
            if(wordsFromConvertedSpeech.containsKey(temp))
                wordsFromConvertedSpeech.put(temp,wordsFromConvertedSpeech.get(temp)+1);
            else
                wordsFromConvertedSpeech.put(temp,1);
        }
        return wordsFromConvertedSpeech;

    }

    public void findRepetition()
    {
        findRepeatedAdjectives(adjectivesList);
        findRepeatedVerbs(verbsList);
    }

    public void findRepeatedAdjectives(ArrayList<String> adjectivesList)
    {
        HashMap<String,Integer> temp = getCountOfWords(convertedSpeech);
        System.out.print("Thisis temp trial " + temp);
        int mainMaxCount =0;
        String mainAlphabet="";
        for(HashMap.Entry<String,Integer> eSet : temp.entrySet())
        {
            //int currentCount = Collections.frequency(temp.entrySet(), eSet.getKey());
            int currentCount = temp.get(eSet.getKey());
            if(adjectivesList.contains(eSet.getKey()))
            {
                    if(mainMaxCount<currentCount) {
                        mainMaxCount = currentCount;
                        mainAlphabet = eSet.getKey().toString();
                    }
            }

        }
        System.out.print("This is the output for the repating stuff "+mainAlphabet+" : "+mainMaxCount);
        raterParameters.put("RepeatedAdjectives",mainAlphabet+":"+mainMaxCount);
        //---------------------
        /*Set<String> unique = new HashSet<String>(verbsList);
        //int maxCount = 0;
        String maxAdjective="";
        for (String key : unique) {
            int currentCount = Collections.frequency(verbsList, key);
            System.out.println(key + ": " + Collections.frequency(verbsList, key));
            if(currentCount>maxCount)
            {
                maxCount=currentCount;
                maxAdjective=key;
            }
            //Log.v("key",key + ": " + Collections.frequency(verbsList, key))
        }
        raterParameters.put("RepeatedAdjectives",maxAdjective+":"+maxCount);*/
    }

    public void findRepeatedVerbs(ArrayList<String> verbsList)
    {
        HashMap<String,Integer> temp = getCountOfWords(convertedSpeech);
        System.out.print("This is temp trial " + temp);
        int mainMaxCount =0;
        String mainAlphabet="";
        for(HashMap.Entry<String,Integer> eSet : temp.entrySet())
        {
            //int currentCount = Collections.frequency(temp.entrySet(), eSet.getKey());
            int currentCount = temp.get(eSet.getKey());
            if(verbsList.contains(eSet.getKey()))
            {
                if(mainMaxCount<currentCount) {
                    mainMaxCount = currentCount;
                    mainAlphabet = eSet.getKey().toString();
                }
            }

        }
        System.out.print("This is the output for the repating stuff "+mainAlphabet+" : "+mainMaxCount);
        raterParameters.put("RepeatedVerbs",mainAlphabet+":"+mainMaxCount);

        /*
        Set<String> unique = new HashSet<String>(verbsList);
        int maxCount = 0;
        String maxVerb="";
        for (String key : unique) {
            int currentCount = Collections.frequency(verbsList, key);
            System.out.println(key + ": " + Collections.frequency(verbsList, key));
            if(currentCount>maxCount)
            {
                maxCount=currentCount;
                maxVerb=key;
            }
            //Log.v("key",key + ": " + Collections.frequency(verbsList, key))
        }
        raterParameters.put("RepeatedVerbs",maxVerb+":"+maxCount);
        */
    }

    // Shvetha's Part

    public void checkLength()
    {
        raterParameters.put("LengthOfSpeech",convertedSpeech.length()+"");

    }

    public void checkGreeting() {
        String[] wordsList = new String[]{"hi", "hello", "morning", "good morning", "evening", "good evening"};
        ArrayList<String> greetingWords = new ArrayList<String>();
        greetingWords.addAll((Arrays.asList(wordsList)));
        for (int i = 0; i < nounList.size(); i++) {
            if (greetingWords.contains(nounList.get(i))) {
                raterParameters.put("Greeting", nounList.get(i));
                break;
            }
        }
    }


    public String[] getSynonym()
    {
        MainActivity op = new MainActivity();
        ArrayList ii = new ArrayList();
        HashMap<String,ArrayList<String>> temp = op.wordsynonyns;
        for(HashMap.Entry<String,ArrayList<String>> en : temp.entrySet())
        {
            ArrayList tempVal = en.getValue();
            for(int i =0;i<tempVal.size();i++)
            {
                ii.add(tempVal.get(i));
            }
        }
        String h[] = new String[ii.size()];
        for(int j=0;j<ii.size();j++)
        {
            h[j]=ii.get(j).toString();
        }
        return h;
    }

    public void checkProfession()
    {
        String professionsList[] = getSynonym();
        String[] TprofessionsList = new String[] {"software","engineer","it","developer","coder"};
        ArrayList<String> possibleProfessions = new ArrayList<String>();
        Log.v("INCHECKPROFESSON",professionsList.toString());
        possibleProfessions.addAll((Arrays.asList(professionsList)));
        for(int i =0;i<nounList.size();i++) {
            if (possibleProfessions.contains(nounList.get(i)))
            {
                Log.v("INCHECKPROFESSON",nounList.get(i));
                raterParameters.put("Profession", nounList.get(i));
                break;
            }

        }
    }
    public void checkEducation()
    {
        String[] educationList = new String[] {"master","bachelor","phd","bachelor's","graduate","master's"};
        ArrayList<String> possibleEducation = new ArrayList<String>();
        possibleEducation.addAll((Arrays.asList(educationList)));
        for(int i =0;i<nounList.size();i++) {
            if (possibleEducation.contains(nounList.get(i)))
            {
                System.out.println("This is from Education: " + nounList.get(i));
                raterParameters.put("Profession", nounList.get(i));
                break;
            }

        }
    }

    public void checkExperience(ArrayList<String> spokenTextExperience, ArrayList<String> possibleExperience)
    {
        for(int i =0;i<spokenTextExperience.size();i++) {
            if (possibleExperience.contains(spokenTextExperience.get(i)))
            {
                System.out.println("This is from experience" + spokenTextExperience.get(i));
                raterParameters.put("Experience", spokenTextExperience.get(i));
                break;
            }

        }
    }

    public int speechRater()
    {
        findRepetition();
        checkEducation();
        checkGreeting();
        checkLength();
        checkProfession();

        int rater = 0;
        System.out.println("This are the number of parameters in our raterParameters Array : "+raterParameters.size());
        for(HashMap.Entry<String,String> temp : raterParameters.entrySet()) {
            System.out.println("Now rater is "+rater);
            if (temp.getKey() != null) {

                if (temp.getKey().equals("RepeatedAdjectives")) {
                    int count = Integer.parseInt(temp.getValue().toString().split(":")[1]);
                    if (count < 3)
                        rater++;
                } else if (temp.getKey().equals("RepeatedVerbs")) {
                    int count = Integer.parseInt(temp.getValue().toString().split(":")[1]);
                    if (count < 3)
                        rater++;
                } else if (temp.getKey().equals("LengthOfSpeech")) {
                    int count = Integer.parseInt(temp.getValue().toString());
                    System.out.println("This is the Length : "+count);
                    if (count < 270)
                        rater++;
                }
                if (temp.getKey().equals("Profession")) {
                    if (temp.getValue() != null)
                        rater++;
                }
                if (temp.getKey().equals("Education")) {
                    if (temp.getValue() != null)
                        rater++;
                }
                if (temp.getKey().equals("Greeting")) {
                    if (temp.getValue() != null)
                        rater++;
                }

            }
        }
            return rater;
    }

    public HashMap<String,String> getFinalRaterHashMap()
    {
        return raterParameters;
    }
}
