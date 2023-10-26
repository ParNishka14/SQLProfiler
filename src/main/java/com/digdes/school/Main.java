package com.digdes.school;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();

        try {
            starter.execute("INSERT VALUES 'id' = 1, 'lastName' = 'Kutuzov', 'active' = false, 'cost' = '100', 'age' = '10' ");
            starter.execute("INSERT VALUES 'id' = 2, 'lastName' = 'Koldushkin', 'active' = true, 'cost' = '341', 'age' = '1' ");
            starter.execute("INSERT VALUES 'id' = 1, 'lastName' = 'Koldushkin2', 'active' = true, 'cost' = '341', 'age' = '1' ");
            starter.execute("INSERT VALUES 'id' = 3, 'lastName' = 'Koldushkin', 'active' = true, 'cost' = '341', 'age' = '1' ");

            starter.execute("DELETE WHERE 'id' = 3");

            starter.execute("SELECT WHERE 'id' = 1");
            starter.execute("SELECT");

            //starter.execute("UPDATE");
        }
        catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}