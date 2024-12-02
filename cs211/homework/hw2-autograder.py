import subprocess

# Make sure the autograder is in the same directory as hw2.c
FILE_NAME = "hw2"

def compile_program():
    compile_result = subprocess.run(["gcc", FILE_NAME+".c", "-o", FILE_NAME], capture_output=True, text=True)
    if compile_result.returncode != 0:
        print("Compilation failed:", compile_result.stderr)
        return False
    return True

def run_test(test_number, test_input, expected_output):
    process = subprocess.Popen(["./"+FILE_NAME, str(test_input)], stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
    stdout, stderr = process.communicate()

    print(f"Test Case {test_number}: Input = {test_input}", end=" ")

    if stdout.strip() == expected_output.strip() or stderr.strip() == expected_output.strip():
        print("✓ Passed")
        return True
    else:
        print("✗ Failed")
        print(f"   Expected: {expected_output}")
        print(f"   Got: {stdout.strip() if stdout != '' else stderr.strip()}")
        return False

def main():
    if not compile_program():
        return

    test_cases = [
        (1, 2147483647, "0Z11 R1UR 11RU 1R11 Z11R 1UR1 1RU1 R11Z"),
        (2, -1, "SZ11 R1UR 11RU 1R11 Z11R 1UR1 1RU1 R11Z"),
        (3, 0, "0000 0000 0000 0000 0000 0000 0000 0000"),
        (4, 2147483648, "Number out of range for a 32-bit integer"),
        (5, 707, "0000 0000 0000 0000 0000 00R0 1R00 001Z"),
		(6, 1923048, "0000 0000 000U 1R01 010R 0UR1 1RU0 R000"),
		(7, -937234823, "SZ00 R000 00R0 0010 Z110 1UR0 0RU1 R00Z"),
        (8, 4, "0000 0000 0000 0000 0000 0000 0000 0100"),
        (9, 48, "0000 0000 0000 0000 0000 0000 00U1 0000")
        # Add more test cases as needed
    ]

    all_passed = True
    for test_number, test_input, expected_output in test_cases:
        if not run_test(test_number, test_input, expected_output):
            all_passed = False

    if all_passed:
        print("All tests passed successfully!")
    else:
        print("Some tests failed.")

if __name__ == "__main__":
    main()
