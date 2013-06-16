//
//  NSArray+Safe.h
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface NSMutableArray (Safe2)

- (id)safeObjectAtIndex:(int)index;

@end
