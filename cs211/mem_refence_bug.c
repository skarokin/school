#include<stdio.h>

typedef struct {
	int a[2];
	double d;
} struct_t;

double fun(int i) {
	volatile struct_t s;
	s.d = 3.14;
	s.a[i] = 1073741824; /* possible out of bounds */
	return s.d;
}

void main() {
    printf(fun(0));

}