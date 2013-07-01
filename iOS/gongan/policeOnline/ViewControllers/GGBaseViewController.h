//
//  GGBaseViewController.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-3.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <UIKit/UIKit.h>
#import <QuartzCore/QuartzCore.h>

@interface GGBaseViewController : UIViewController

+(GGBaseViewController *)createInstance;

-(void)setMyTitle:(NSString *)aTitle;

-(void)showLoadingHUD;
-(void)hideLoadingHUD;
-(void)showLoadingHUDTxt:(NSString *) txt;
-(void)alertNetError;

@end
