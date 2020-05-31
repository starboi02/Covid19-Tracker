package com.example.covid_19tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static LinkedHashMap<String, List<String>> getData() {
        LinkedHashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

        List<String> qs1 = new ArrayList<String>();
        qs1.add("Coronaviruses are a large family of viruses which may cause illness in animals or humans.  In humans, several coronaviruses are known to cause respiratory infections ranging from the common cold to more severe diseases such as Middle East Respiratory Syndrome (MERS) and Severe Acute Respiratory Syndrome (SARS). The most recently discovered coronavirus causes coronavirus disease COVID-19.");

        List<String> qs2 = new ArrayList<String>();
        qs2.add("COVID-19 is the infectious disease caused by the most recently discovered coronavirus. This new virus and disease were unknown before the outbreak began in Wuhan, China, in December 2019. COVID-19 is now a pandemic affecting many countries globally.");


        List<String> qs3 = new ArrayList<String>();
        qs3.add("People can catch COVID-19 from others who have the virus. The disease can spread from person to person through small droplets from the nose or mouth which are spread when a person with COVID-19 coughs or exhales. These droplets land on objects and surfaces around the person. Other people then catch COVID-19 by touching these objects or surfaces, then touching their eyes, nose or mouth. People can also catch COVID-19 if they breathe in droplets from a person with COVID-19 who coughs out or exhales droplets. This is why it is important to stay more than 1 meter (3 feet) away from a person who is sick.");

        List<String> qs4 = new ArrayList<String>();
        qs4.add("The most common symptoms of COVID-19 are fever, tiredness, and dry cough. Some patients may have aches and pains, nasal congestion, runny nose, sore throat or diarrhea. These symptoms are usually mild and begin gradually. Some people become infected but don’t develop any symptoms and don't feel unwell. Most people (about 80%) recover from the disease without needing special treatment. Around 1 out of every 6 people who gets COVID-19 becomes seriously ill and develops difficulty breathing. Older people, and those with underlying medical problems like high blood pressure, heart problems or diabetes, are more likely to develop serious illness. People with fever, cough and difficulty breathing should seek medical attention.");

        List<String> qs5 = new ArrayList<String>();
        qs5.add("COVID-19 is mainly spread through respiratory droplets expelled by someone who is coughing or has other symptoms such as fever or tiredness. Many people with COVID-19 experience only mild symptoms. This is particularly true in the early stages of the disease. It is possible to catch COVID-19 from someone who has just a mild cough and does not feel ill. Some reports have indicated that people with no symptoms can transmit the virus. It is not yet known how often it happens. WHO is assessing ongoing research on the topic and will continue to share updated findings.");

        List<String> qs6 = new ArrayList<String>();
        qs6.add("Practicing hand and respiratory hygiene is important at ALL times and is the best way to protect others and yourself.\n" +
                "When possible maintain at least a 1 meter distance between yourself and others. This is especially important if you are standing by someone who is coughing or sneezing.  Since some infected persons may not yet be exhibiting symptoms or their symptoms may be mild, maintaining a physical distance with everyone is a good idea if you are in an area where COVID-19 is circulating.");

        List<String> qs7 = new ArrayList<String>();
        qs7.add("If you have been in close contact with someone with COVID-19, you may be infected.\n" +
                "Close contact means that you live with or have been in settings of less than 1 metre from those who have the disease. In these cases, it is best to stay at home.\n" +
                "If you have definitely had COVID-19 (confirmed by a test) self-isolate for 14 days even after symptoms have disappeared as a precautionary measure – it is not yet known exactly how long people remain infectious after they have recovered. Follow national advice on self-isolation.");

        List<String> qs8 = new ArrayList<String>();
        qs8.add("Self-isolation is an important measure taken by those who have COVID-19 symptoms to avoid infecting others in the community, including family members.\n" +
                "Self-isolation is when a person who is experiencing fever, cough or other COVID-19 symptoms stays at home and does not go to work, school or public places. This can be voluntarily or based on his/her health care provider’s recommendation.\n" +
                "If a person is in self-isolation, it is because he/she is ill but not severely ill (requiring medical attention)\n" +
                "have a large, well-ventilated with hand-hygiene and toilet facilities\n" +
                "If this is not possible, place beds at least 1 metre apart\n" +
                "Keep at least 1 metre from others, even from your family members\n" +
                "Monitor your symptoms daily\n" +
                "Isolate for 14 days, even if you feel healthy\n" +
                "If you develop difficulty breathing, contact your healthcare provider immediately – call them first if possible\n" +
                "Stay positive and energized by keeping in touch with loved ones by phone or online, and by exercising yourself at home.");

        List<String> qs9 = new ArrayList<String>();
        qs9.add("While some western, traditional or home remedies may provide comfort and alleviate symptoms of mild COVID-19, there are no medicines that have been shown to prevent or cure the disease. WHO does not recommend self-medication with any medicines, including antibiotics, as a prevention or cure for COVID-19. However, there are several ongoing clinical trials of both western and traditional medicines. WHO is coordinating efforts to develop vaccines and medicines to prevent and treat COVID-19 and will continue to provide updated information as soon research results become available.\n" +
                "The most effective ways to protect yourself and others against COVID-19 are to:\n" +
                "Clean your hands frequently and thoroughly\n" +
                "\n" +
                "Avoid touching your eyes, mouth and nose\n" +
                "\n" +
                "Cover your cough with the bend of elbow or tissue. If a tissue is used, discard it immediately and wash your hands.\n" +
                "\n" +
                "Maintain a distance of at least 1 metre from others.");

        List<String> qs10 = new ArrayList<String>();
        qs10.add("The time between exposure to COVID-19 and the moment when symptoms start is commonly around five to six days but can range from 1 – 14 days.");

        expandableListDetail.put("What is Coronavirus ?", qs1);
        expandableListDetail.put("What is Covid19 ?", qs2);
        expandableListDetail.put("How does COVID-19 spread? ", qs3);
        expandableListDetail.put("What are the Symptoms of Covid19 ?", qs4);
        expandableListDetail.put("Can COVID-19 be caught from a person who has no symptoms ?",qs5);
        expandableListDetail.put("How can we protect others and ourselves if we don't know who is infected ?",qs6);
        expandableListDetail.put("What should I do if I have come in close contact with someone who has COVID-19?",qs7);
        expandableListDetail.put("What does it mean to self-isolate?",qs8);
        expandableListDetail.put("Is there a vaccine, drug or treatment for COVID-19?",qs9);
        expandableListDetail.put("How long does it take after exposure to COVID-19 to develop symptoms?",qs10);
        return expandableListDetail;
    }
}
