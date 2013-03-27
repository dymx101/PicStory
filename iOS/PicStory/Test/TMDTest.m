//
//  TMDTest.m
//  PicStory
//
//  Created by Yim Daniel on 13-2-7.
//  Copyright (c) 2013年 Yim Daniel. All rights reserved.
//

#import "TMDTest.h"
#import "ASIFormDataRequest.h"

@implementation TMDTest

+(void)uploadImage:(UIImage *)aImage url:(NSURL *)aURL delegate:(id)aDelegate
{
    ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:aURL];
    
    [ASIFormDataRequest requestWithURL:aURL];
    
    NSData *imageData = UIImageJPEGRepresentation(aImage, .7f);
    
    if (imageData)
    {
        [request setData:imageData withFileName:@"selectedImage.jpg" andContentType:@"image/jpeg" forKey:@"imageFile"];
    }
    
    request.delegate = aDelegate;
    
    [request startSynchronous];
    
}

+(void)testUrl:(NSURL *)aURL  delegate:(id)aDelegate
{
    ASIFormDataRequest *request = [ASIFormDataRequest requestWithURL:aURL];
    
    [ASIFormDataRequest requestWithURL:aURL];
        
    request.delegate = aDelegate;
    
    [request startSynchronous];

}
@end
