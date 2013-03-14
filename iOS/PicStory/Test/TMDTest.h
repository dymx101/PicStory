//
//  TMDTest.h
//  PicStory
//
//  Created by Yim Daniel on 13-2-7.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import <Foundation/Foundation.h>

@interface TMDTest : NSObject
+(void)uploadImage:(UIImage *)aImage url:(NSURL *)aURL  delegate:(id)aDelegate;
@end

//http://localhost:8080/picStoryServer/upload.do
