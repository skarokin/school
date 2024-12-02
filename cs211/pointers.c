#include<stdio.h>

int main() {
    int a = 5;
    int b = 10;

    int *ptr1 = &a;
    int *ptr2 = &b;

    // type warning: *ptr2 is not an integer
    ptr1 = *ptr2;    // ptr1's value is content stored at memory address b
    printf("ptr1's value is the content stored at memory address b %d\n", ptr1);
    ptr1 = ptr2;     // ptr1's value is memory address of b
    printf("%d\n", ptr1);

    // ptr1 does not change!
}