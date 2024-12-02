// Created by AJ DiLeo
// For use in CS211 Fall 2023 ONLY

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include <limits.h>

#define MAX_BIT_POSITION 32

// Cache for storing FizzBuzz results for each bit position
char cache[MAX_BIT_POSITION][3];

// Initialize the cache with FizzBuzz results
void preComputeResults() {
    // TODO: For every possible position, pre-compute its corresponding string output
	// Make sure to handle the case where you will not replace the bit with Z, R, or U
	// Your advancedBitwiseFizzBuzz() code should know when to use the replacement and when to use the bit
    // Use 'Z' for numbers divisible by 3 and 5, 'R' for divisible by 3, and 'U' for divisible by 5

    // if bit at position i is 1, perform replacements
    // if bit at position i is 0, place the actual bit in the cache
    for (int i = 0; i < MAX_BIT_POSITION; i++) {
        if ((i % 3 == 0) && (i % 5 == 0)) {
            cache[i][0] = 'Z';
            cache[i][1] = '\0';
        } else if (i % 3 == 0) {
            cache[i][0] = 'R';
            cache[i][1] = '\0';
        } else if (i % 5 == 0) {
            cache[i][0] = 'U';
            cache[i][1] = '\0';
        } else {
            cache[i][0] = '.';
            cache[i][1] = '\0';
        }
    }
}

// Retrieve the FizzBuzz result for a specific bit position
char* getFizzBuzzForBit(int position) {
    // TODO: Return the FizzBuzz result for the given position from the cache
    if (position >= MAX_BIT_POSITION) {
        return NULL;
    }

    return cache[position];
}

// Perform the advanced Bitwise FizzBuzz logic on the given number
void advancedBitwiseFizzBuzz(int32_t N) {
    // TODO: Implement the advanced Bitwise FizzBuzz logic
    // - For each bit in the number, apply the FizzBuzz logic
    // - Replace the MSb with 'S' if it's set
    // - Print each bit or its FizzBuzz result
    // - Format the output with a space every four bits

    // loop through each bit of decimal N
    // from LSB to MSB (i.e. in reverse order)
    // check if each bit is set (i.e. == 1)
    for (int i = MAX_BIT_POSITION - 1; i >= 0; i--) {

        // get the bit at position i and check if it is set
        int currentBit = (N >> i);
        int isBitSet = currentBit & 1;

        // if sign bit, print 'S' if bit is set
        if ((i == 31) && isBitSet) {
            printf("S");
        } else if (isBitSet) {
            // if cache[i] is './0' then print the bit
            char* fetchCache = getFizzBuzzForBit(i);
            if (strcmp(fetchCache, ".\0") == 0) {
                printf("%d", isBitSet);
            } else {
                printf("%s", fetchCache);
            }
        // if bit is not set, always print 0
        } else {
            printf("0");
        }

        if (i % 4 == 0) {
            printf(" ");
        }

    } 

	// Each bitstring should be outputted from Left -> Right, MSb -> LSb
	// Index 0 of the bitstring should be the LSb
	// E.g., 
	// 1  0  0 1 0 0 0 1 1 1 0 0   <=== bitstring
	// 11 10 9 8 7 6 5 4 3 2 1 0   <=== indices
}

// Main function to run the program
int main(int argc, char *argv[]) {
    // TODO: Parse the command line argument to get the input number
    // Use strtol to convert the string to a long integer
    // Check if the number is within the range of a 32-bit signed integer
	// If so, print "Number out of range for a 32-bit integer\n"
    long int inputNumber = strtol(argv[1], NULL, 10);

    if (inputNumber > INT32_MAX || inputNumber < INT32_MIN) {
        printf("Number out of range for a 32-bit integer\n");
        return 0;
    }

    int32_t inputNumber32 = (int32_t)inputNumber;

    // TODO: Initialize the cache
    preComputeResults();

    // TODO: Call advancedBitwiseFizzBuzz with the parsed number
    advancedBitwiseFizzBuzz(inputNumber32);

	// Make sure the number is an int32_t
    return 0;
}
