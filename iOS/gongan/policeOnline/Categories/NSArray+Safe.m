//
//  NSArray+Safe.m
//  policeOnline
//
//  Created by towne on 13-6-17.
//  Copyright (c) 2013年 tmd. All rights reserved.
//

#import "NSArray+Safe.h"

@implementation NSArray (Safe)

- (id)safeObjectAtIndex:(int)index
{
    if (index>=0 && index<[self count]) {
        return [self objectAtIndex:index];
    } else {
        return nil;
    }
}

@end
