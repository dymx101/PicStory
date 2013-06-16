//
//  NSArray+Safe.m
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "NSMutableArray+Safe2.h"

@implementation NSMutableArray (Safe2)

- (id)safeObjectAtIndex:(int)index
{
    if (index>=0 && index<[self count]) {
        return [self objectAtIndex:index];
    } else {
        return nil;
    }
}

@end
