package com.digdes.school;

import java.util.*;

public class JavaSchoolStarter {
    public List<Map<String, Object>> list = new ArrayList<>();


    public JavaSchoolStarter() {

    }
    public List<Map<String, Object>> execute(String request) {

        switch (readRequest(request)) {
            case "INSERT" -> insertListElement(request);
            case "UPDATE" -> updateListElement(request);
            case "DELETE" -> deleteListElement(request);
            case "SELECT" -> selectListElement(request);
            default -> throw new RuntimeException("Incorrect request.");
        }
        return list;
    }

    private void insertListElement(String request) {
        String key = "";
        String value = "";
        char[] requestArray = request.replaceAll(" ", "").toCharArray();
        Map<String, Object> row = new HashMap<>();
        boolean keyFlag = false;
        boolean valueFlag = false;

        System.out.println(request);
        for (int i = 0; i < request.length(); i++) {
            if(i >= requestArray.length) {
                break;
            }
            if (String.valueOf(requestArray[i]).equals("=")) {
                i++;
                valueFlag = true;
            }
            else if(String.valueOf(requestArray[i]).equals("'")) {
                i++;
                keyFlag = true;
            }

            if(!keyFlag) {
                if(!valueFlag) {
                    if (i+1 == requestArray.length) {
                        break;
                    }
                }
                else {
                    if (String.valueOf(requestArray[i]).equals("'")) {
                        i++;
                    }
                    value = value + requestArray[i];
                    if (String.valueOf(requestArray[i+1]).equals("'")) {
                        i++;
                        valueFlag = false;

                        row.put(key, value);

                        value = "";
                        key = "";
                    }
                    else if(String.valueOf(requestArray[i+1]).equals(",")) {
                        valueFlag = false;

                        row.put(key, value);
                        value = "";
                        key = "";
                    }
                }
            }
            else {
                key = key + requestArray[i];
                if(i == requestArray.length - 1) {
                    break;
                }
                else if (String.valueOf(requestArray[i+1]).equals("'")) {
                    i++;
                    keyFlag = false;
                }
            }

        }

        list.add(row);
    }

    private void updateListElement(String request) {
        String key = "";
        String value = "";
        String keyUpdate = "";
        String valueUpdate = "";
        char[] requestArray = request.replaceAll(" ", "").toCharArray();
        boolean keyFlag = false;
        boolean valueFlag = false;
        String whereCheck = "";
        Map<String, Object> updateRow = new HashMap<>();

        for (int i=0; i < request.length(); i++) {
            if(i >= requestArray.length) {
                break;
            }

            whereCheck = whereCheck + requestArray[i];

            if (whereCheck.equals("WHERE")) {
                if(String.valueOf(requestArray[i]).equals("'")) {
                    i++;
                    keyFlag = true;
                }
                if(keyFlag) {
                    keyUpdate = keyUpdate + requestArray[i];

                    if(String.valueOf(requestArray[i+1]).equals("'")) {
                        keyFlag = false;
                        i++;
                    }
                }

                if(String.valueOf(requestArray[i]).equals("=")) {
                    i++;
                    if (String.valueOf(requestArray[i]).equals("'")) {
                        i++;
                    }
                    if (i >= requestArray.length) {
                        break;
                    }
                    else {
                        if(!String.valueOf(requestArray[i]).equals("'")) {
                            valueUpdate = valueUpdate + requestArray[i];
                        }
                    }
                }
            }
            else {
                if (String.valueOf(requestArray[i]).equals("=")) {
                    i++;
                    valueFlag = true;
                }
                else if(String.valueOf(requestArray[i]).equals("'")) {
                    i++;
                    keyFlag = true;
                }

                if(!keyFlag) {
                    if(!valueFlag) {
                        if (i+1 == requestArray.length) {
                            break;
                        }
                    }
                    else {
                        if (String.valueOf(requestArray[i]).equals("'")) {
                            i++;
                        }

                        value = value + requestArray[i];
                        if (i+1 >= requestArray.length) {
                            break;
                        }
                        if (String.valueOf(requestArray[i+1]).equals("'")) {
                            i++;
                            valueFlag = false;

                            updateRow.put(key, value);
                            value = "";
                            key = "";
                        }
                        else if(String.valueOf(requestArray[i+1]).equals(",")) {
                            valueFlag = false;
                            whereCheck = "";

                            updateRow.put(key, value);
                            value = "";
                            key = "";
                        }
                    }
                }
                else {
                    key = key + requestArray[i];
                    if(i == requestArray.length - 1) {
                        break;
                    }
                    else if (String.valueOf(requestArray[i+1]).equals("'")) {
                        i++;
                        keyFlag = false;
                        whereCheck = "";
                    }
                }
            }
        }
        for (int g=0; g < list.size(); g++) {
            if (list.get(g).get(keyUpdate) == valueUpdate) {
                for (String keyMap: list.get(g).keySet()) {
                    for (String updateKeyMap: updateRow.keySet()) {
                        if (keyMap.equals(updateKeyMap)) {
                            list.get(g).replace(keyMap, updateRow.get(updateKeyMap));
                        }
                    }
                }
            }
        }
    }

    private void deleteListElement(String request) {
        String key = "";
        String value = "";
        if(request.equals("DELETE")) {
            list.clear();
        }
        else {
            char[] requestArray = request.replaceAll(" ", "").toCharArray();
            boolean keyFlag = false;
            System.out.println(request.replaceAll(" ", ""));
            for(int s=0; s < requestArray.length; s++) {
                if(String.valueOf(requestArray[s]).equals("'")) {
                    s++;
                    keyFlag = true;
                }
                if(keyFlag) {
                    key = key + requestArray[s];

                    if(String.valueOf(requestArray[s+1]).equals("'")) {
                        keyFlag = false;
                        s++;
                    }
                }

                if(String.valueOf(requestArray[s]).equals("=")) {
                    s++;
                    if (String.valueOf(requestArray[s]).equals("'")) {
                        s++;
                    }
                    if (s >= requestArray.length) {
                        break;
                    }
                    else {
                        if(!String.valueOf(requestArray[s]).equals("'")) {
                            value = value + requestArray[s];
                        }
                    }
                }
            }
        }

        for (int w = list.size() - 1; w >= 0; w--) {
            if(list.get(w).get(key).equals(value)) {
                removeElement(w);
            }
        }
    }

    private void removeElement(int i){
        list.remove(i);
    }

    private void selectListElement(String request) {
        if (request.equals("SELECT")) {
            printTable("","");
        }
        else {
            String key = "";
            String value = "";
            char[] requestArray = request.replaceAll(" ", "").toCharArray();
            boolean keyFlag = false;
            System.out.println(request.replaceAll(" ", ""));
            for(int s=0; s < requestArray.length; s++) {
                if(String.valueOf(requestArray[s]).equals("'")) {
                    s++;
                    keyFlag = true;
                }
                if(keyFlag) {
                    key = key + requestArray[s];

                    if(String.valueOf(requestArray[s+1]).equals("'")) {
                        keyFlag = false;
                        s++;
                    }
                }

                if(String.valueOf(requestArray[s]).equals("=")) {
                    s++;
                    if (String.valueOf(requestArray[s]).equals("'")) {
                        s++;
                    }
                    if (s >= requestArray.length) {
                        break;
                    }
                    else {
                        if(!String.valueOf(requestArray[s]).equals("'")) {
                            value = value + requestArray[s];
                        }
                    }
                }
            }
            printTable(key, value);
        }
    }

    private void printTable(String key, String value){
        if(key.equals("") && value.equals("")){
            System.out.printf("\n");
            System.out.printf("%3s | %-15s | %-5s | %-5s | %-10s \n", "id", "lastName", "Age", "Cost", "Active");
            System.out.println("---------------------------------------------------");
            for(int i = 0; i < list.size(); i++){
                System.out.printf("%3s | %-15s | %-5s | %-5s | %-10s \n", list.get(i).get("id"), list.get(i).get("lastName"), list.get(i).get("age"), list.get(i).get("cost"), list.get(i).get("active"));
            }
            System.out.printf("\n");
        } else {
            System.out.printf("\n");
            System.out.printf("%3s | %-15s | %-5s | %-5s | %-10s \n", "id", "lastName", "Age", "Cost", "Active");
            System.out.println("---------------------------------------------------");
            for(int i = 0; i < list.size(); i++){
                if(list.get(i).get(key).equals(value)){
                    System.out.printf("%3s | %-15s | %-5s | %-5s | %-10s \n", list.get(i).get("id"), list.get(i).get("lastName"), list.get(i).get("age"), list.get(i).get("cost"), list.get(i).get("active"));
                }
            }
            System.out.printf("\n");
        }
    }

    private String readRequest(String request) {
        if ((request.startsWith("UPDATE")) || (request.startsWith("INSERT"))
        || (request.startsWith("DELETE")) || (request.startsWith("SELECT"))) {
            return request.substring(0, 6);
        }
        else {
            throw new RuntimeException("Incorrect request.");
        }
    }
}
