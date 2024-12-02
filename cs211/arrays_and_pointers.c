#include<stdio.h>
#include<stdint.h>

int main(void) {
    int *p;
    int array[10];
    int count = 0;

    for (p = array; p < &array[10]; p++) {
        *p = count++;
        printf("%d\n", array[*p]);
    }

}