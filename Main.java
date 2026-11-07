import java.util.Scanner;
import java.util.ArrayList;

public class Main{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        
        //NAME INFO AND INPUT//
        System.out.println("=============================");
        System.out.println("Enter your Name: ");
        String name = sc.nextLine();

        if(name.trim().isEmpty()){
            throw new IllegalArgumentException("Name cannot be empty.");
        }
        
        if(name.length()> 15){
            throw new IllegalArgumentException("Name cannot be more than 15 character long.");
        }
        
        // FOR OPTIONS 
        int choice;
        ArrayList<Task> tasks = new ArrayList<>();
    
        do{
            // HOME PAGE //
            System.out.println("=============================");
            System.out.println("======= Welcome " + name + " =======");
            System.out.println("--------MY TO-DO LIST--------"); 
            System.out.println("  1. My Tasks        ");
            System.out.println("  2. Add Task        ");
            System.out.println("  3. Manage Tasks    ");
            System.out.println("  4. Delete Tasks    ");
            System.out.println("  5. Exit            ");
            
            // OPTION SELECTION
            System.out.println("Enter you choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            
            // MY TASKS //
            if (choice == 1){
                System.out.println("=============================");
                System.out.println("======My Tasks selected======");
                
                if(tasks.isEmpty()){
                    System.out.println("No tasks available");
                }else{
                    for (int i=0; i < tasks.size(); i++){
                        System.out.println((i+1) + ". " + tasks.get(i).taskName);

                        if(tasks.get(i).isCompleted){
                            System.out.println("   Status : COMPLETED");
                        }else{
                            System.out.println("   Status : PENDING");
                        }
                    }
                }
            
            // ADD TASKS //
            }else if (choice == 2){
                System.out.println("=============================");
                System.out.println("======Add Task selected======");
                System.out.println("Enter the Task: ");
                
                String taskName = sc.nextLine();
                
                if(taskName.trim().isEmpty()){
                    System.out.println("Task cannot be empty");
                }else{
                    Task newTask = new Task(taskName);
                    tasks.add(newTask);
                    System.out.println("Task added Succesfully");
                }
            
            // MANAGE TASKS //
            }else if (choice == 3){
                System.out.println("=============================");
                System.out.println("====Manage Tasks selected====");

                if(tasks.isEmpty()){
                    System.out.println("No tasks available");
                }else{
                    for(int i = 0; i < tasks.size(); i++){
                        System.out.println((i+1) + ". " + tasks.get(i).taskName);

                        if(tasks.get(i).isCompleted){
                            System.out.println("   Status: Completed");
                        }else{
                            System.out.println("   Status: Pending");
                        }
                    }
                    System.out.println("Enter the task number to mark as completed: ");
                    int taskNumber = sc.nextInt();
                    sc.nextLine();

                    if(taskNumber >= 1 && taskNumber <= tasks.size()){

                        if(tasks.get(taskNumber - 1).isCompleted){
                            System.out.println("Task is already completed.");
                        }else{
                            tasks.get(taskNumber - 1).markCompleted();
                            System.out.println("Task marked as completed.");
                        }

                    }else{
                        System.out.println("Invalid task number");
                    }
                }
            
            // DELETE TASKS //
            }else if(choice == 4){
                System.out.println("=============================");
                System.out.println("====Delete Tasks selected====");

                if(tasks.isEmpty()) {
                    System.out.println("No task available to delete");
                }else{ 
                    for(int i = 0; i< tasks.size();i++){
                        System.out.println((i+1) + ". " + tasks.get(i).taskName);
                    }

                    System.out.println("Enter the task you want to delete: ");
                    int tasknumber = sc.nextInt();
                    sc.nextLine();
                    
                    System.out.println("Are you sure you want to delete this task? (yes/no)");
                    String yesno = sc.nextLine();
                    if (yesno.equalsIgnoreCase("yes")){
                        if(tasknumber >= 1 && tasknumber <= tasks.size()){
                            tasks.remove(tasknumber - 1);
                            System.out.println("Selected task is deleted");
                        }else{
                            System.out.println("Invalid task selected");
                        }
                    }else{
                        System.out.println("Task deletion cancelled.");
                    }
                    System.out.println("=============================");
                    
                    if(tasks.size() == 1){
                        System.out.println("==========Your Task==========");
                        System.out.println("1. " + tasks.get(0).taskName);
                    }else if(tasks.size() == 0){
                        System.out.println("==========Your Task==========");
                        System.out.println("NONE");
                    }else{
                        System.out.println("=========Your Tasks==========");
                        
                        for(int i = 0; i< tasks.size();i++){
                        System.out.println((i+1) + ". " + tasks.get(i).taskName);
                        }
                    }
                }
            
            // EXIT // 
            }else if (choice == 5){
                System.out.println("=============================");
                System.out.println("XOXOXO-Goodbye, " + name + "!-OXOXOX");
            
            }else{
                System.out.println("=============================");
                System.out.println("Invalid Selection");
            }
     
        }while(choice != 5);
               
        sc.close();
    }
}