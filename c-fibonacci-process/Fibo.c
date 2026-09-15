///////////////////////////////////////
//
// This C program creates a child process from the parent process.
// The child process generates the Fibonacci Sequence 
//
// CSC 389: HW-03
// Rachel Ratajczak
///////////////////////////////////////

#include <stdio.h>
#include <unistd.h>
#include <pthread.h>
#include <sys/wait.h>


// Uses the input from the user as parameter for the fibo function
// Function to generate the fibonacci sequence 
void Fibo(int userInput) {
    
    int fib1 = 0;
    int fib2 = 1;
    int result;

    // Use a loop to add the last two numbers to get the next in the sequence 
    for (int i = 0; i < userInput; i++){
        // 0, 1, 1, 2
        printf("%i ",fib1);
        result = fib1 + fib2;
        fib1 = fib2;
        fib2 = result;
    }

    printf("\n");
}
  
// Call Fibo function to generate sequence from user input
int main(){

    // User inputs the number that is to be generated 
    int userInput;
    printf("Enter a number to generate the fibonacci sequence of that number: \n");
    scanf("%d", &userInput);

    // Perform error checking to make sure the input is valid 
    if (userInput <= 0){
        printf("Invalid input. Please enter a positive number");
    }

    // Use fork() system call to create child process
    pid_t id = fork();

    if (id < 0) { /* Error occurred */
        fprintf(stderr, "Fork failed\n");
        return 1;
    
    // Child process generates and outputs Fibonacci sequence 
    } else if (id == 0){
        Fibo(userInput);
    
    // Parent process invokes wait() system call for child process to complete before exiting program
    } else {
        wait(NULL);
    }

 return 0;

}
