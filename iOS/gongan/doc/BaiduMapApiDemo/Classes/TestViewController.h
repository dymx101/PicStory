//
//  TestViewController.h
//  BaiduMapApiDemoSrc
//
//  Created by 孙振兴 on 11-10-13.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import <UIKit/UIKit.h>
#import "BMapKit.h"

@interface TestViewController : UIViewController<BMKMapViewDelegate> {
	IBOutlet BMKMapView* mapView;
	IBOutlet UITextField* _text;
	BMKPointAnnotation* _annotation;
	BOOL _flag;
}

-(IBAction)onClickOK;
- (void)mapView:(BMKMapView *)mapView regionDidChangeAnimated:(BOOL)animated;

@end
