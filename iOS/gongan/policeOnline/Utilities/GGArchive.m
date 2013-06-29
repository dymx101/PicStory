//
//  GGArchive.m
//  policeOnline
//
//  Created by towne on 13-6-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "GGArchive.h"

@implementation GGArchive

/**
 *  功能:存档
 */
+ (void)archiveData:(id<NSCoding>)aData withFileName:(NSString *)aFileName
{
    NSArray *documentArray = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentPath = [documentArray objectAtIndex:0];
    NSString *filePath = [documentPath stringByAppendingPathComponent:aFileName];
    
    NSData *archiveData = [NSKeyedArchiver archivedDataWithRootObject:aData];
    [archiveData writeToFile:filePath atomically:NO];
}

/**
 *  功能:取档
 */
+ (id<NSCoding>)unarchiveDataWithFileName:(NSString *)aFileName
{
    NSArray *documentArray = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *documentPath = [documentArray objectAtIndex:0];
    NSString *filePath = [documentPath stringByAppendingPathComponent:aFileName];
        id unarchiveData = [NSKeyedUnarchiver unarchiveObjectWithFile:filePath];
        return unarchiveData;
}

@end
