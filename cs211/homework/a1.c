// Created by AJ DiLeo
// For use in CS211 Fall 2023 ONLY

#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <time.h>

#define MAX_INPUT_SIZE 1024
#define MAX_ARGS_SIZE 100

typedef struct programmingLanguage {
	int id;
	char* language;
	int year;
	char* creator;
	char* paradigm;
	double popularityIndex;

	int isDeleted; // internal field, not accessible to user
} language_t;

// step 1: create structs for the other two tables: operatingSystems
// and databases
// include internal field

typedef struct operatingSystems {
	int id;
	char* name;
	int year;
	char* developer;
	char* kernelType;

	int isDeleted;  // internal field, not accessible to user
} os_t;

typedef struct databases {
	int id;
	char* name;
	int year;
	char* type;
	char* developer;

	int isDeleted;  // internal field, not accessible to user
} database_t;

// step 2: create typedef struct for storing metadata
typedef struct metadata {
	int count;      // store current count of records in a table
	int nextIndex;  // store index of next available slot in the array(table)
	int maxCount;   // store max # of records (instances of structs) that can be stored in the table
} metadata_t;

// step 3: declare the two other arrays of structs
// programmingLanguages has been defined for you already
// TODO: add operatingSystems and databases
language_t* programmingLanguages;
os_t* operatingSystems;
database_t* databases;

// step 4: declare 3 metadata structs, one for each table
metadata_t programmingLanguagesMetadata;
metadata_t operatingSystemsMetadata;
metadata_t databasesMetadata;

// step 5: jump to L167

// This function takes the user's input and splits it by spaces
// into an array of strings, ignoring spaces that are wrapped in quotes
// There is no need to modify this code.
// You do not need to understand this code
// but you are welcome to research its application
void splitInput(char* input, char** args, int* arg_count) {
    *arg_count = 0;
    int in_quotes = 0; // Flag to track whether we are inside quotes
    char* token_start = input;

    for (char* ptr = input; *ptr != '\0'; ptr++) {
        if (*ptr == '"') {
            in_quotes = !in_quotes; // Toggle the in_quotes flag when a quote is encountered
        }

        if ((*ptr == ' ' || *ptr == '\n') && !in_quotes) {
            // If not inside quotes and a space or newline is found, consider it as a separator
            *ptr = '\0'; // Replace space or newline with null terminator
            args[(*arg_count)++] = token_start;
            token_start = ptr + 1; // Start of the next token
        }
    }

    // Add the last token (if any) after the loop
    if (*token_start != '\0') {
        // Remove leading and trailing double quotes if they exist
        if (token_start[0] == '"' && token_start[strlen(token_start) - 1] == '"') {
            token_start[strlen(token_start) - 1] = '\0'; // Remove trailing quote
            args[(*arg_count)++] = token_start + 1; // Remove leading quote
        } else {
            args[(*arg_count)++] = token_start;
        }
    }
    args[*arg_count] = NULL;
}

// step 7: implement setup function
// this function is responsible for dynamically allocating the
// particular table. Use the tables declared on L27.
void setup(char* table, int numRows) {

	if (strcmp(table, "programmingLanguages") == 0) {
		// allocate memory for programmingLanguages
		programmingLanguages = malloc(numRows*sizeof(language_t));
		
		// update metadata
		programmingLanguagesMetadata.count = 0;
		programmingLanguagesMetadata.nextIndex = 0;
		programmingLanguagesMetadata.maxCount = numRows;
	} else if (strcmp(table, "operatingSystems") == 0) {
		// allocate memory for operatingSystems
		operatingSystems = malloc(numRows*sizeof(os_t));

		// update metadata
		operatingSystemsMetadata.count = 0;
		operatingSystemsMetadata.nextIndex = 0;
		operatingSystemsMetadata.maxCount = numRows;
	} else if (strcmp(table, "databases") == 0) {
		// allocate memory for databases
		databases = malloc(numRows*sizeof(database_t));

		// update metadata
		databasesMetadata.count = 0;
		databasesMetadata.nextIndex = 0;
		databasesMetadata.maxCount = numRows;
	}

	// DO NOT TOUCH THIS PRINT
	// REQUIRED FOR AUTOGRADER
	printf("setup complete\n");
}

// step 8: implement insert function
// this function is responsible for updating the corresponding
// fields of the struct located at the next available index
// make sure to use and update your metadata.

// autograder print for insufficient capacity:
// fprintf(stderr, "cannot insert due to insufficient capacity.\n");
void insert(char** args) {

	/*
	* 1. check which table to insert into
	* 2. check if chosen array has enough storage (use metadata)
	*    if insufficient space, print "cannot insert due to insufficient capacity.\n"
	* 3. insert to next available index (found in metadata) - order of records should be old to new
	* 4. update metadata
	*/

	// {int id, char* language, int year, char* creator, char* paradigm, double popularityIndex}
	if (strcmp(args[1], "programmingLanguages") == 0) {

		if (programmingLanguagesMetadata.count >= programmingLanguagesMetadata.maxCount) {
			fprintf(stderr, "cannot insert due to insufficient capacity.\n");
			return;
		}
		
		int nextIndex = programmingLanguagesMetadata.nextIndex;

		language_t* newProgrammingLanguage = &programmingLanguages[nextIndex];
		newProgrammingLanguage->id = atoi(args[2]);	
		newProgrammingLanguage->language = strdup(args[3]);
		newProgrammingLanguage->year = atoi(args[4]);
		newProgrammingLanguage->creator = strdup(args[5]);
		newProgrammingLanguage->paradigm = strdup(args[6]);
		newProgrammingLanguage->popularityIndex = atof(args[7]);

		newProgrammingLanguage->isDeleted = 0;

		programmingLanguagesMetadata.count++;
		programmingLanguagesMetadata.nextIndex++;

	// {int id, char* name, int year, char* developer, char* kernelType}
	} else if (strcmp(args[1], "operatingSystems") == 0) {

		if (operatingSystemsMetadata.count >= operatingSystemsMetadata.maxCount) {
			fprintf(stderr, "cannot insert due to insufficient capacity.\n");
			return;
		}

		int nextIndex = operatingSystemsMetadata.nextIndex;

		os_t* newOperatingSystem = &operatingSystems[nextIndex];
		newOperatingSystem->id = atoi(args[2]);	
		newOperatingSystem->name = strdup(args[3]);
		newOperatingSystem->year = atoi(args[4]);
		newOperatingSystem->developer = strdup(args[5]);
		newOperatingSystem->kernelType = strdup(args[6]);

		newOperatingSystem->isDeleted = 0;

		operatingSystemsMetadata.count++;
		operatingSystemsMetadata.nextIndex++;

	// {int id, char* name, int year, char* type, char* developer}
	} else if (strcmp(args[1], "databases") == 0) {

		if (databasesMetadata.count >= databasesMetadata.maxCount) {
			fprintf(stderr, "cannot insert due to insufficient capacity.\n");
			return;
		}

		int nextIndex = databasesMetadata.nextIndex;

		database_t* newDatabase = &databases[nextIndex];
		newDatabase->id = atoi(args[2]);
		newDatabase->name = strdup(args[3]);
		newDatabase->year = atoi(args[4]);
		newDatabase->type = strdup(args[5]);
		newDatabase->developer = strdup(args[6]);

		newDatabase->isDeleted = 0;
		
		databasesMetadata.count++;
		databasesMetadata.nextIndex++;

	} 

	// DO NOT TOUCH THIS PRINT
	// REQUIRED FOR AUTOGRADER
	printf("insert complete\n");
}

// step 9: implement soft delete function
// this function is responsible for marking a record as deleted
// you should be updating an internal field flag so that get will
// not display this record. 
// You should not be attempting to free/overwrite this record - it remains alloc
// with a updated field
// make use of your metadata
void delete(char* table, int id) {

	// free the memory allocated for the specified struct
	if (strcmp(table, "programmingLanguages") == 0) {

		// you want to use POINTER ARITHMETIC here
		for (language_t* ptr = programmingLanguages; ptr < programmingLanguages + programmingLanguagesMetadata.count; ptr++) {
			if (ptr->id == id) {
				ptr->isDeleted = 1;
			}
		}

	} else if (strcmp(table, "operatingSystems") == 0) {

		for (os_t* ptr = operatingSystems; ptr < operatingSystems + operatingSystemsMetadata.count; ptr++) {
			if (ptr->id == id) {
				ptr->isDeleted = 1;
			}
		}

	} else if (strcmp(table, "databases") == 0) {

		for (database_t* ptr = databases; ptr < databases + databasesMetadata.count; ptr++) {
			if (ptr->id == id) {
				ptr->isDeleted = 1;
			}
		}

	}

	// DO NOT TOUCH THIS PRINT
	// REQUIRED FOR AUTOGRADER
	printf("delete complete\n");
}

// step 10: implement modify function
// this function is responsible for overwriting all of the contents of all
// records that match an ID.
// make use of your metadata
// !!!NOTE: The structs store pointers. Make sure to free any allocated
// memory before overwriting it!!!
void modify(char** args) {

	char* table = args[1];
	int id = atoi(args[2]);

	if (strcmp(table, "programmingLanguages") == 0) {

		// find the record with maching id and overwrite it contents
		// ensure that the record is not deleted
		for (language_t* ptr = programmingLanguages; ptr < programmingLanguages + programmingLanguagesMetadata.count; ptr++) {
			if (ptr->id == id && ptr->isDeleted == 0) {
				// free memory for the strings, since they are a char pointer
				free(ptr->language);
				free(ptr->creator);
				free(ptr->paradigm);
				ptr->id = atoi(args[3]);
				ptr->language = strdup(args[4]);
				ptr->year = atoi(args[5]);
				ptr->creator = strdup(args[6]);
				ptr->paradigm = strdup(args[7]);
				ptr->popularityIndex = atof(args[8]);
			}
		}

	} else if (strcmp(table, "operatingSystems") == 0) {

		// find the record with maching id and overwrite it contents
		// ensure that the record is not deleted
		for (os_t* ptr = operatingSystems; ptr < operatingSystems + operatingSystemsMetadata.count; ptr++) {
			if (ptr->id == id && ptr->isDeleted == 0) {
				// free memory for the strings, since they are a char pointer
				free(ptr->name);
				free(ptr->developer);
				free(ptr->kernelType);
				ptr->id = atoi(args[3]);
				ptr->name = strdup(args[4]);
				ptr->year = atoi(args[5]);
				ptr->developer = strdup(args[6]);
				ptr->kernelType = strdup(args[7]);
			}
		}

	} else if (strcmp(table, "databases") == 0) {

		// find the record with maching id and overwrite it contents
		// ensure that the record is not deleted
		for (database_t* ptr = databases; ptr < databases + databasesMetadata.count; ptr++) {
			if (ptr->id == id && ptr->isDeleted == 0) {
				// free memory for the strings, since they are a char pointer
				free(ptr->name);
				free(ptr->type);
				free(ptr->developer);
				ptr->id = atoi(args[3]);
				ptr->name = strdup(args[4]);
				ptr->year = atoi(args[5]);
				ptr->type = strdup(args[6]);
				ptr->developer = strdup(args[7]);
			}
		}

	}

	// DO NOT TOUCH THIS PRINT
	// REQUIRED FOR AUTOGRADER
	printf("modify complete\n");
}

// step 11: implement get function
// this function is responsible for fetching all non-deleted records
// make use of your metadata
// Make sure to follow guidelines for format in writeup
// see examples as well
// Use %lf for formatting double data type
void get(char* table) {

	// {int id, char* language, int year, char* creator, char* paradigm, double popularityIndex}
	if (strcmp(table, "programmingLanguages") == 0) {
		printf("id,language,year,creator,paradigm,popularityIndex\n");

		for (language_t* ptr = programmingLanguages; ptr < programmingLanguages + programmingLanguagesMetadata.count; ptr++) {
			
			if (ptr->isDeleted == 0) {
				printf("%d,%s,%d,%s,%s,%lf\n", ptr->id, ptr->language, ptr->year, ptr->creator, ptr->paradigm, ptr->popularityIndex);
			}

		}

	// {int id, char* name, int year, char* developer, char* kernelType}
	} else if (strcmp(table, "operatingSystems") == 0) {
		printf("id,name,year,developer,kernelType\n");

		for (os_t* ptr = operatingSystems; ptr < operatingSystems + operatingSystemsMetadata.count; ptr++) {
			if (ptr->isDeleted == 0) {
				printf("%d,%s,%d,%s,%s\n", ptr->id, ptr->name, ptr->year, ptr->developer, ptr->kernelType);
			}
		}

	// {int id, char* name, int year, char* type, char* developer}
	} else if (strcmp(table, "databases") == 0) {
		printf("id,name,year,type,developer\n");

		for (database_t* ptr = databases; ptr < databases + databasesMetadata.count; ptr++) {
			if (ptr->isDeleted == 0) {
				printf("%d,%s,%d,%s,%s\n", ptr->id, ptr->name, ptr->year, ptr->type, ptr->developer);
			}
		}

	}

}

// step 12: implement exit function
// this function should free all allocated memory
// Make sure to avoid memory leaks by freeing any allocated memory
// inside a struct (char*) before freeing the struct pointer
void exitProgram() { 

	for (language_t* ptr = programmingLanguages; ptr < programmingLanguages + programmingLanguagesMetadata.count; ptr++) {
		if (ptr != NULL) {
			free(ptr->language);
			free(ptr->creator);
			free(ptr->paradigm);
		}
	}
	
	if (programmingLanguages != NULL) free(programmingLanguages);

	for (os_t* ptr = operatingSystems; ptr < operatingSystems + operatingSystemsMetadata.count; ptr++) {
		if (ptr != NULL) {
			free(ptr->name);
			free(ptr->developer);
			free(ptr->kernelType);
		}
	}

	if (operatingSystems != NULL) free(operatingSystems);
	
	for (database_t* ptr = databases; ptr < databases + databasesMetadata.count; ptr++) {
		if (ptr != NULL) {
 			free(ptr->name);
			free(ptr->type);
			free(ptr->developer);
		}
	}
	// ensure not NULL before freeing
	if (databases != NULL) free(databases);
	
	exit(0);
	
}

// this code is responsible for parsing the user's
// input, and determining based on the command
// which function to send it to.
// You do not have to modify this code, but you should
// understand it.
void execute_cmd(char** args, int arg_count) {
	char* cmd = args[0];
	if (strcmp(cmd, "setup") == 0) {
		setup(args[1], atoi(args[2]));
	} else if (strcmp(cmd, "insert") == 0) {
		insert(args);
	} else if (strcmp(cmd, "delete") == 0) {
		delete(args[1], atoi(args[2]));
	} else if (strcmp(cmd, "modify") == 0) {
		modify(args);
	} else if (strcmp(cmd, "get") == 0) {
		get(args[1]);
	} else if (strcmp(cmd, "exit") == 0) {
		exitProgram();
	} else {
		printf("\n");
	}
}

// step 6: initialize the default metadata values here
// jump to L76
void initializeMetadata() {
	// we initialize maxCount in setup()
	programmingLanguagesMetadata = (metadata_t) { .count = 0, .nextIndex = 0 };
	operatingSystemsMetadata     = (metadata_t) { .count = 0, .nextIndex = 0 };
	databasesMetadata            = (metadata_t) { .count = 0, .nextIndex = 0 };
}

// this code creates the interactive shell
// you do not need to modify this
// You do not need to understand this code
// but you are welcome to research its application
void cmd_loop() {
	char input[MAX_INPUT_SIZE];
    ssize_t bytes_read;
	printf("Usage: \n");
	printf("setup {table} {numRows}\n");
	printf("insert {table} {data}\n");
	printf("delete {table} {id}\n");
	printf("modify {table} {id} {data}\n");
	printf("get {table}\n\n");
	initializeMetadata();
    while (1) {
        printf("CS211> ");
		fflush(stdout);
        
        // Read user input using the read() system call
        bytes_read = read(STDIN_FILENO, input, sizeof(input));
        
        if (bytes_read == -1) {
            perror("read");
            exit(EXIT_FAILURE);
        }
        
        if (bytes_read == 0) {
			printf("\n");
            break;
        }
        
        // Null-terminate the input
        input[bytes_read] = '\0';

		char** args = (char**)malloc(MAX_ARGS_SIZE * sizeof(char*));
		int arg_count;

		splitInput(input, args, &arg_count);
        
        // Execute the user's command
        execute_cmd(args, arg_count);
		free(args);
    }
}


int main() {
	cmd_loop();
}