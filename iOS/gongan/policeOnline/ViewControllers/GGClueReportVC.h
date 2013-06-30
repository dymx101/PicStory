//
//  GGClueReport.h
//  policeOnline
//
//  Created by Dong Yiming on 6/15/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface GGClueReportVC : GGBaseViewController
<UIScrollViewDelegate
, UITextFieldDelegate
, UITextViewDelegate
, UIActionSheetDelegate
, UINavigationControllerDelegate
, UIImagePickerControllerDelegate>

@property (assign) long long contentID;

@end
