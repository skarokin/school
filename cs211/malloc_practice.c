#include<stdio.h>
#include<stdlib.h>

int main(int argc, char **argv) {
	int array_size;
	
	printf("How big would you like the array? \n");
	scanf("%d", &array_size);

	int* heap_array = malloc(array_size * sizeof(int));

	for (int i = 0; i < array_size; i++) {
		heap_array[i] = 15;
		printf("heap_array[%d] = %d\n", i, heap_array[i]);
	}
}