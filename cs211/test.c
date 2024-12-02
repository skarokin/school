#include<stdio.h>

long plus (long x, long y) {
	return x + y;
}

void sumstore(long x, long y, long* dest) {
	long t = plus(x, y);
	*dest = t;
}

int main() {
	long a = 5;
	long* ptr = &a;

	sumstore(2, 4, ptr);
	printf("%ld", a);
	
	return 0;
}