#include<stdio.h>
#include<stdlib.h>

int main(int argc, char *argv[]) {
	// allocate memory in the heap the size of 1 integer
	int* ptr = (int*)malloc(sizeof(int));

	// for array pointers, do...
	// int* array_ptr = (int*)malloc(sizeof(int)*array_length);

	if (ptr == NULL) {
		// malloc returns a null pointer if it couldn't allocate memory
		printf("Unable to allocate memory! Oh no");
		return -1;
	}
	*ptr = 69;

	// print the value of the pointer and the pointer address
	printf("%d at %p\n", *ptr, ptr);

	free(ptr); // frees the memory taken up by pointer

	// allocate space for an array of 5 elements
	int* array = (int*)calloc(5, sizeof(int));

	for (int i = 0; i < 5; i++) {
		printf("|%d", array[i]);
	}
	printf("|\n");

	// easily resize arrays using calloc
	int* new_array = (int*)realloc(array,10*sizeof(int));
	array = new_array;

	for (int i = 0; i < 10; i++) {
		printf("|%d", array[i]);
	}
	printf("|\n");

	free(array);

	return 0;
}