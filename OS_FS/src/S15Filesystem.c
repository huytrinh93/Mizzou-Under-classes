#include <stdlib.h>
#include <string.h>
#include <ctype.h>
#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <VirtualBlockStorage.h>
#include <fcntl.h>

#include "../include/S15Filesystem.h"

#define VBS_FILENAME "HARD_DRIVE"

/*
 * Gives accesses to the VBS for all functions inside this .c
 * only. The fs_mount function will provide VBS structure
 * for usage with the vbs API functions
 * */
static VBS_Type *virtualBlockStorage;

int fs_mount() {
	
    vbs_initialize(VBS_FILENAME);
    virtualBlockStorage = vbs_open(VBS_FILENAME);	
    // ====================================
    // BEGIN: Initialize Filesystem on VBS
    // ====================================
    struct inode * inode, * next;	
    int i;
    inode =next;
    // Write your FS Initialization Here
    BlockType rwBlock = vbs_make_block(); 
    printf("%s\n", "TESTING: Create Blocks");   
    // ====================================
    // END: Initialize Filesystem on VBS
    // ===================================
	return 0;
}



int fs_create_file(const char* absoluteFilename,FileType fileType) {
//check inputs
if(absoluteFilename ==NULL || absoluteFilename[0] =='\0' || (fileType !=REG_FILE && fileType!=DIR_FILE))
		printf("Error");	

//create file with name and fileType and return file descriptor
	VBS_Index startBlockOfFile;
	VBS_Index idx = startBlockOfFile = vbs_nextFreeBlock(virtualBlockStorage);
	int copiedBlocks = 0;
	int newFile = open(absoluteFilename, O_CREAT | O_WRONLY);
                idx = 8;
                idx = startBlockOfFile;
                if (newFile != -1)  do
                {
                        BlockType rwBlock = vbs_read(virtualBlockStorage, idx++);
//write first empty block
                        write(newFile, rwBlock.buffer, VBS_BLOCK_SIZE);

                        vbs_release_block(rwBlock);
                }
                while ( idx < startBlockOfFile + copiedBlocks );
//close the file
                close(newFile);
	return 1;

}

int fs_get_directory (const char* absolutePath, Directory_t* directoryContents) {
	if(absolutePath[0] =='\0' || directoryContents ==NULL)
		printf("Error");
	return 0;
}

int fs_seek_within_file (const char* absoluteFilename, unsigned int offset, unsigned int orgin) {
	if(absoluteFilename[0] =='\0' || offset<0 || orgin <0)
		printf("Error");
	return 0;
}

int fs_remove_file(const char* absoluteFilename) {
	if(absoluteFilename[0] =='\0')
		printf("Error");
	remove(absoluteFilename);
	return 0;

}
/*
 * Block that check parameter, if fail then return 0 else return 1
 * Use in write/read file
 */
int check_parameter_file(const char* a, void* b, unsigned int c)
{
	if(a==NULL || a[0] =='\0' || b ==NULL || c<0|| c>1024)
		{return 0;}
	else 
		{return 1;}
}

int fs_write_file(const char* absoluteFilename, void* dataToBeWritten, unsigned int numberOfBytes) {
//validate parameters
	int checking=check_parameter_file(absoluteFilename, dataToBeWritten,numberOfBytes);
	if(checking==0){
		printf("Error: Invalid input");
		return 0;
	}
//start by input block of file        
	VBS_Index startBlockOfFile;
        VBS_Index idx = startBlockOfFile = vbs_nextFreeBlock(virtualBlockStorage);
        int copiedBlocks = 0;
//open file then write to it
	int originalFile = open(absoluteFilename, O_WRONLY);
                size_t readBytes = 0;
                if (originalFile != -1)  do
                {
                        BlockType rwBlock = vbs_make_block();
                        readBytes = read(originalFile, rwBlock.buffer, VBS_BLOCK_SIZE);
                        vbs_write(virtualBlockStorage, idx++, rwBlock);
                        ++copiedBlocks;
//release memory
                        vbs_release_block(rwBlock);
                }
                while ( VBS_BLOCK_SIZE ==  readBytes );

                close(originalFile);
	return 0;
}

int fs_read_file(const char* absoluteFilename, void* dataToBeRead, unsigned int numberOfBytes) {
//validate parameters
	int checking=check_parameter_file(absoluteFilename, dataToBeRead,numberOfBytes);
	if(checking==0){
		printf("Error: Invalid input");
		return 0;
	}
//start by input block of file
        VBS_Index startBlockOfFile;
        VBS_Index idx = startBlockOfFile = vbs_nextFreeBlock(virtualBlockStorage);
        int copiedBlocks = 0;
//open file then read from it
        int originalFile = open(absoluteFilename, O_RDONLY);
                size_t readBytes = 0;
                if (originalFile != -1)  do
                {
                        BlockType rwBlock = vbs_make_block();
                        readBytes = read(originalFile, rwBlock.buffer, VBS_BLOCK_SIZE); 
                        vbs_write(virtualBlockStorage, idx++, rwBlock);
                        ++copiedBlocks;
//release memory
                        vbs_release_block(rwBlock);
                }
                while ( VBS_BLOCK_SIZE ==  readBytes );

                close(originalFile);
	return 0;
}

/*
 * Doesn't truncate the file, just closes the filesystem access
 * to the vbs file used.
 * */
int fs_unmount() {
	return vbs_close(virtualBlockStorage);
}


