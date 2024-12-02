#include<stdio.h>

int main(int argc, char **argv) {

    int matrix[2][3];

    matrix[1][0] = 17;
    matrix[0][3] = 42;

    printf("matrix[1][0]%d\n", matrix[1][0]);
    printf("matrix[0][3]%d\n", matrix[0][3]);

    return 0;

}