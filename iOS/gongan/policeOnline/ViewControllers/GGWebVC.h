//
//  GGWebVC.h
//  WeiGongAn
//
//  Created by dong yiming on 13-4-21.
//  Copyright (c) 2013年 WeiGongAn. All rights reserved.
//

#import <UIKit/UIKit.h>
@class GGWanted;
@class GGClue;

@interface GGWebVC : GGBaseViewController <UIWebViewDelegate>
@property (copy) NSString *urlStr;
@property (copy) NSString *naviTitleString;
@property (strong,nonatomic) GGWanted *wanted;
@property (strong,nonatomic) GGClue *clue;
@property (nonatomic) BOOL wantedKeep;  // 通缉令 收藏 1 or 取消收藏 0

-(IBAction)myFavoriteClick:(id)sender;
-(void) addAnClue;
@end
