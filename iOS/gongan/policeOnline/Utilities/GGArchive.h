//
//  GGArchive.h
//  policeOnline
//
//  Created by towne on 13-6-30.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface GGArchive : NSObject

/**
 *  功能:存档
 */
+ (void)archiveData:(id<NSCoding>)aData withFileName:(NSString *)aFileName;

/**
 *  功能:取档
 */
+ (id<NSCoding>)unarchiveDataWithFileName:(NSString *)aFileName;

@end
