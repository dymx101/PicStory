//
//  TMDViewController.h
//  CameraDemo
//
//  Created by dong yiming on 13-2-23.
//  Copyright (c) 2013年 yim. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <MobileCoreServices/MobileCoreServices.h>

@interface TMDViewController : UIViewController
<UINavigationControllerDelegate
, UIImagePickerControllerDelegate
, UIVideoEditorControllerDelegate>

@property (nonatomic, strong) NSURL *videoURLToEdit;

@end
