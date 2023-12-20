//
// Created by dayona on 12/20/23.
//

#ifndef DAYDARK_UTILITIES_H
#define DAYDARK_UTILITIES_H

#include "dirent.h"
#include "sys/stat.h"

void LSDIR(string dir) {
    struct dirent *d;
    DIR *openDir;
    openDir = opendir(dir.c_str());
    if (openDir != nullptr) {
        for (d = readdir(openDir); d != nullptr; d = readdir(openDir)) {
            LOGD("%s", d->d_name);
        }
        closedir(openDir);
    } else {
        LOGD("\nError Occurred!");
    }
}

string READ_FILE(string filePath) {
    char line[1000] = {0};
    FILE *fp = fopen(filePath.c_str(), "rt");
    if (fp == nullptr) {
        return "FILE PATH DOESN'T EXIT";
    }
    string output;
    while (fgets(line, sizeof(line), fp)) {
        output += line;
    }
    fclose(fp);
    return output;
}

void WRITE_FILE(string path, string data) {
    FILE *file = fopen(path.c_str(), "w");
    if (file == nullptr) {
        LOGD("PATH DOESN'T EXIST");
        return;
    }
    fprintf(file, "%s\n", data.c_str());
    fclose(file);
}

void MKDIR(string dirName) {
    bool mk = mkdir(dirName.c_str(), 0777);
    if (mk) {
        LOGD("DIRECTORY CREATED");
    } else {
        LOGD("CANNOT CREATE DIRECTORY");
    }
}

#endif //DAYDARK_UTILITIES_H
