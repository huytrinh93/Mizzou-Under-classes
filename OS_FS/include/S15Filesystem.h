#ifndef _S15_FILESYSTEM_
#define _S15_FILESYSTEM_

#include <stdbool.h>
#include <string.h>

#define MAX_FILENAME 63

typedef enum FILE_OPTIONS {REG_FILE,DIR_FILE} FileType;

typedef struct {
	size_t size;
	struct inode * inode[1020];
	short next;
}FSBlock;

typedef struct {
	unsigned char filename[MAX_FILENAME + 1];
	unsigned char inodeIdx; //0 - 255 possible inodes
}DirectoryEntry_t;

typedef struct {
	
	DirectoryEntry_t *entries;
	size_t size;

}Directory_t;

/*
 * Provide Doxygen or Java document comments for the following
 * functions of your filesystem
 * */


int fs_mount();

int fs_create_file(const char* absoluteFilename, FileType fileType);

int fs_get_directory (const char* absolutePath, Directory_t* directoryContents); 
int fs_seek_within_file (const char* absoluteFilename, unsigned int offset, unsigned int orgin);

int fs_remove_file(const char* absoluteFilename);

int fs_write_file(const char* absoluteFilename, void* dataTobeWritten, unsigned int numberOfBytes);

int fs_read_file(const char* absoluteFilename, void* dataTobeRead, unsigned int numberOfBytes);

int fs_unmount(); 

#endif
