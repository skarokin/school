// Created by AJ DiLeo
// For use in CS211 Fall 2023 ONLY

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>

// ******STEP 0: SKIP TO MAIN FUNCTION*********

// TODO: Nested Reverse
// Input: 2d array, number of rows, number of cols
// For each array, reverse its position
// For each element in the array, reverse its position
// e.g, input: 
// [
// 	[1, 2, 3, 4, 5],
// 	[6, 7, 8, 9, 10],
// 	[1, 3, 6, 8, 9]
// ]
// first reverse the order of the arrays
// [
// 	[1, 3, 6, 8, 9],
// 	[6, 7, 8, 9, 10],
// 	[1, 2, 3, 4, 5]
// ]
// now reverse the order of each of the elements
// [
// 	[9, 8, 6, 3, 1],
// 	[10, 9, 8, 7, 6],
// 	[5, 4, 3, 2, 1]
// ]
// DONE!
// NOTE: You will notice the code for the two steps will be fairly similar
void nestedReverse(int** arr, int rows, int cols) {
	// reverse rows by swapping the pointers 
	// only go up to rows/2 because we are swapping 1st and last, 2nd and last-1, etc.
	for (int i = 0; i < rows/2; i++) {
		int* temp = arr[i];
		arr[i] = arr[rows-i-1];
		arr[rows-i-1] = temp;
	}

	// reverse order of each element
	// only go to cols/2 because we are swapping 1st and last, 2nd and last-1, etc.
	for (int i = 0; i < rows; i++) {
		for (int j = 0; j < cols/2; j++) {
			int temp = arr[i][j];
			arr[i][j] = arr[i][cols-j-1];
			arr[i][cols-j-1] = temp;
		}
	}

	return;
}

typedef struct Student {
	int id;
	char* name;
	float gpa;
} student_t;
// It is a common naming convention to append
// a _t to a struct to denote that its a struct

// TODO: Deep Compare
// Input array of students, total number of students
// For every combination (numStudents C 2) of students, see if they all match
// e.g., students = [0,1,2,3]:
// combos are : 0,1  0,2  0,3  1,2  1,3  2,3
int deepCompare(student_t* students, int numStudents) {

	// use a nested for loop (this is deep compare!)
	for (int i = 0; i < numStudents; i++) {
		for (int j = i + 1; j < numStudents; j++) {
			// strcmp is string compare; if the two strings are identical returns 0
			if (students[i].id != students[j].id 
			|| strcmp(students[i].name, students[j].name) != 0
			|| students[i].gpa != students[j].gpa) {
				return 0;
			}
		}
	}

	return 1;

	/* 
	* alternatively, we can use pointers to reference array elements
	* student_t* end = students + numStudents;
    * for(student_t* i = students; i < end; i++) {
    *    for(student_t* j = i + 1; j < end; j++) {
    *        if(i->id != j->id || strcmp(i->name, j->name) != 0 || i->gpa != j->gpa) {
    *            return 0;
    *        }
    *    }
    * }
    * return 1;
	*/
}

// TODO: Replace random
// Input: int array, number of elements to replace
// ASSUME: numToReplace <= length of arr
// Replace the first numToReplace elements with a random number
// e.g., arr = [1,2,3,4,5], numToReplace=2
// Output: arr = []
// ONLY USE rand() function. `man 3 rand`
void replaceNRandom(int* arr, int numToReplace) {

	// Do not touch this line
	srand(time(0));
	// use rand() to generate a random number
	
	for (int i = 0; i < numToReplace; i++) {
		arr[i] = rand();
	}

	return;
}

int main(int argc, char* argv[]) {
	// argc - number of cmd line args
	// argv - array of cmd line args

	// Step 1: Ensure at least two command line args were given
	// e.g., ./hw1 {pointers|structs|malloc}
	if (argc < 2) {
		// NOTE: Notice how we must include a new line char (\n)
		// NOTE: What is stderr? What is the difference between stdout & stderr?
		fprintf(stderr, "Usage: ./hw1 {pointers|structs|malloc}\n");
		// NOTE: EXIT_FAILURE is a macro. This makes our code cleaner.
		return EXIT_FAILURE;
	}

	// Step 2: Now that we know what exercise to run, let's setup conditionals
	// to enforce this
	// NOTE: In your terminal, run `man strcmp`: This will give us the documentation
	// strcmp(char*, char*) -> 0 if eq
	// NOTE: Why is it argv[1]? What is argv[0]?
	if (strcmp(argv[1], "pointers") == 0) {
		// At this point, the user has entered ./hw1 pointers
		// The following code will setup our 2d array.
		// It is not necessary to understand this code yet, but take a stab at it.

		// ./hw1 pointers {rows} {cols} {elements}
		// atoi converts a char* to an int
		int rows = atoi(argv[2]);
		int cols = atoi(argv[3]);
		// We are taking a 2d array as input
		// Thus, rows*cols is the number of elements in the array
		// We need to add 4 to skip the first four arguments: 
		// program name, exercise name, num rows, num cols
		if ((rows * cols) + 4 != argc) {
			fprintf(stderr, "Usage: ./hw1 pointers {rows} {cols} {rows*cols elements}\n");
			return EXIT_FAILURE;
		}
		
		// We are now ready to load the 2d array into memory
		// THIS IS OUR INPUT 2D ARRAY
		// first, we need to allocate enough room for num rows arrays
		int** arr = (int**)malloc(rows * sizeof(int*));
		for (int i = 0; i < rows; i++) {
			// now, lets allocate enough room for num cols elements in each array
			arr[i] = (int*)malloc(cols * sizeof(int));
			for (int j = 0; j < cols; j++) {
				// now lets load the elements
				arr[i][j] = atoi(argv[4 + i * cols + j]);
			}
		}

		// Now arr is a 2d array loaded with elements from command line args
		// Step 3: Call your nestedReverse function
		// This function will reverse the order of the arrays, and each of their elements.
		// Thus, no need for a return value.
		nestedReverse(arr, rows, cols);

		// Now our arr is reversed. 

		// DO NOT TOUCH THE FOLLOWING CODE
		// FORMAT IS REQUIRED FOR AUTO-GRADER
		// ANY CHANGES WILL RESULT IN INVALID SCORE
		// Print the reversed 2D array
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				printf("%d ", arr[i][j]);
			}
			printf("\n");
			free(arr[i]);
		}
	} else if (strcmp(argv[1], "structs") == 0) {
		// At this point, the user has entered ./hw1 structs
		// each struct requires 3 elements: id, name, gpa

		// ./hw1 students {numStudents} {data}
		// ./hw1 students 2 1 aj 3.5 2 zoe 4.0
		int numStudents = atoi(argv[2]);

		if (argc != (numStudents*3) + 3) {
			fprintf(stderr, "Usage: ./hw1 structs {numStudents} {data}\n");
			return EXIT_FAILURE;
		}

		// allocate enough space for an array of numStudents student_t structs.
		student_t* students = (student_t*)malloc(numStudents * sizeof(student_t));
		// load the data
		for (int i = 0; i < numStudents; i++) {
			students[i].id = atoi(argv[3 + i * 3]);
			students[i].name = strdup(argv[4 + i * 3]);
			// STEP 4: Add the data for GPA. Use the examples above for assistance.
			// Remember, gpa is a float. What function should you use to convert
			// char* to float?
			students[i].gpa = atof(argv[5 + i * 3]);
		}

		int result = deepCompare(students, numStudents);

		// DO NOT TOUCH THE FOLLOWING CODE
		// FORMAT IS REQUIRED FOR AUTO-GRADER
		// ANY CHANGES WILL RESULT IN INVALID SCORE
		// Print the result
		if (result == 1) {
			printf("Data is the same!\n");
		} else {
			printf("At least one student is different!\n");
		}

		// release allocated memory
		// Why do we need to free name first before the entire array?
		// a char* is a dynamically alloc string => We do not know how long the string is
		// until the user enters it.
		// We have to free the inside of the array, before the entire array
		// else, that memory will forever be allocated
		for (int i = 0; i < numStudents; i++) {
			free(students[i].name);
		}
		free(students);
	} else if (strcmp(argv[1], "malloc") == 0) {
		// At this point user has entered ./hw1 malloc

		// STEP 5: Extract numToReplace and numElements
		int numToReplace = atoi(argv[2]);
		int numElements = atoi(argv[3]);

		// STEP 6: Add a check to ensure user has passed correct num of args
		// check "pointers" for an example
		// ./hw1 malloc {numToReplace} {numElements} {elements}
		// ./hw1 malloc 2 5 1 2 3 4 5
		// You can assume numToReplace <= numElements
		// You can assume all elements will be integers
		if (argc != 4 + atoi(argv[3])) {
			fprintf(stderr, "Usage: ./hw1 malloc {numToReplace} {numElements} {data} \n");
		}

		// STEP 7: Now load the array into memory
		// allocate enough space for a variable named arr
		// iterate over all elements and load them into their respective slots
		int* arr = (int*)malloc(numElements*sizeof(int));
		int count = 4;

		for (int i = 0; i < numElements; i++) {
			arr[i] = atoi(argv[count]);
			count++;
		}

		// STEP 8: replace the first numToReplace elements in the array
		replaceNRandom(arr, numToReplace);

		// STEP 9: Modify numElements and arr variable name as needed.
		// DO NOT touch print statement, this is used for auto-grader
		// output should be:
		// 1 2 3 4 5
		for (int i = 0; i < numElements; i++) {
			printf("%d ", arr[i]);
		}
		printf("\n");

		// STEP 10: Free the allocated memory
		free(arr);
		
	}

	return EXIT_SUCCESS;
}