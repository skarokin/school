#include <stdio.h>
#include <stdlib.h>

// a function that calculates pow(a, b)
int power(int base, int exponent) {
    int result = 1;
    while (exponent > 0) {
        result *= base;
        exponent--;
    }
    return result;
}

int main(int argc, char *argv[]) {
    // if number of arguments is not 3, incorrect usage of program
    if (argc != 3) {
        printf("Usage: %s <base> <exponent>\n", argv[0]);
        return 1;
    }

	printf("first argument is: %s\n", argv[0]);

    int base = atoi(argv[1]);
    int exponent = atoi(argv[2]);

    int result = power(base, exponent);

    printf("%d^%d = %d\n", base, exponent, result);

    return 0;
}
