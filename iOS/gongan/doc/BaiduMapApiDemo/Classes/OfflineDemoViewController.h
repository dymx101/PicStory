//
//  OfflineDemoViewController.h
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"

@interface OfflineDemoViewController : UIViewController<BMKMapViewDelegate, BMKOfflineMapDelegate, UITextFieldDelegate> {
	IBOutlet BMKMapView* mapView;
	IBOutlet UITextField* _cityText;
	IBOutlet UITextField* _cityIDText;
	IBOutlet UILabel* _label;
	BMKOfflineMap* _offlineMap;
	UIAlertView*   _alertView;
}

//-(IBAction)onClickSearch;
-(IBAction)onClickScan;
//-(IBAction)onClickStart;
//-(IBAction)onClickStop;
-(IBAction)onClickDel;
-(IBAction)onClickGet;

@end
