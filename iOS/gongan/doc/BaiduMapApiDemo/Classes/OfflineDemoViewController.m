//
//  OfflineDemoViewController.m
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import "OfflineDemoViewController.h"


@implementation OfflineDemoViewController
// The designated initializer.  Override if you create the controller programmatically and want to perform customization that is not appropriate for viewDidLoad.
/*
 - (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil {
 self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
 if (self) {
 // Custom initialization.
 }
 return self;
 }
 */


// Implement viewDidLoad to do additional setup after loading the view, typically from a nib.
- (void)viewDidLoad {
    [super viewDidLoad];
	
	_offlineMap = [[BMKOfflineMap alloc]init];
	_offlineMap.delegate = self;
	_cityText.delegate = self;
	_cityIDText.delegate = self;
	BMKOLUpdateElement* element = [_offlineMap getUpdateInfo:0];
	NSLog(@"%@", element.cityName);
}

- (void)viewWillAppear:(BOOL)animated
{
    mapView.delegate = self;
}

/*
 // Override to allow orientations other than the default portrait orientation.
 - (BOOL)shouldAutorotateToInterfaceOrientation:(UIInterfaceOrientation)interfaceOrientation {
 // Return YES for supported orientations.
 return (interfaceOrientation == UIInterfaceOrientationPortrait);
 }
 */

- (void)didReceiveMemoryWarning {
    // Releases the view if it doesn't have a superview.
    [super didReceiveMemoryWarning];
    
    // Release any cached data, images, etc. that aren't in use.
}

- (void)viewDidUnload {
    [super viewDidUnload];
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}


- (void)dealloc {
    [super dealloc];
	if (_alertView != nil) {
		[_alertView release];
		_alertView = nil;
	}
}

/*-(IBAction)onClickSearch
 {
 NSArray* records = [_offlineMap searchCity:_cityText.text];
 if (records == nil || records.count != 1) {
 [records release];
 return;
 }
 _cityIDText.text = [NSString stringWithFormat:@"%d", ((BMKOLSearchRecord*)[records objectAtIndex:0]).cityID];
 [records release];
 }*/

-(IBAction)onClickScan
{
	if ([_offlineMap scan]) {
		if (_alertView == nil) {
			_alertView = [[UIAlertView alloc] initWithTitle:@"扫描离线图" message:@"扫描中。。。" delegate:nil cancelButtonTitle:nil otherButtonTitles:nil, nil];
		}
		[_alertView show];
	}
}

/*-(IBAction)onClickStart
 {
 int cityid = -1;
 cityid = [_cityIDText.text intValue];
 if ([_offlineMap start:cityid]) {
 NSLog(@"OfflineDemo start cityid:%d", cityid);
 } else {
 NSLog(@"OfflineDemo not start cityid:%d", cityid);
 }
 
 }
 
 -(IBAction)onClickStop
 {
 int cityid = -1;
 cityid = [_cityIDText.text intValue];
 if ([_offlineMap pasue:cityid]) {
 NSLog(@"OfflineDemo stop cityid:%d", cityid);
 } else {
 NSLog(@"OfflineDemo not stop cityid:%d", cityid);
 }
 }*/

-(IBAction)onClickDel
{
	int cityid = -1;
	cityid = [_cityIDText.text intValue];
	if ([_offlineMap remove:cityid]) {
		NSString* str = [NSString stringWithFormat:@"OfflineDemo del cityid:%d", cityid];
		NSLog(@"%@", str);
		_label.text = str;
	} else {
		NSString* str = [NSString stringWithFormat:@"OfflineDemo not del cityid:%d", cityid];
		NSLog(@"%@", str);
		_label.text = str;
	}
}

-(IBAction)onClickGet
{
	int cityid = -1;
	cityid = [_cityIDText.text intValue];
	BMKOLUpdateElement* element = [_offlineMap getUpdateInfo:cityid];
	if (element != nil) {
		_label.text = [NSString stringWithFormat:@"%@ 大小:%.2fMB", element.cityName, ((double)element.size)/1000000];
		[element release];
	} else {
		_label.text = nil;
	}
	
}

- (void)onGetOfflineMapState:(int)type withState:(int)state
{
	switch (type) {
			
			/*case TYPE_OFFLINE_UPDATE:
			 {
			 NSLog(@"OfflineDemo cityid:%d update", state);
			 BMKOLUpdateElement* update = [_offlineMap getUpdateInfo:state];
			 _label.text = [NSString stringWithFormat:@"%@ : %d%%", update.cityName, update.ratio];
			 [update release];
			 }
			 break;*/
		case TYPE_OFFLINE_UNZIP:
			NSLog(@"OfflineDemao unzip zip file %d", state);
			_alertView.message = [NSString stringWithFormat:@"OfflineDemao unzip zip file %d", state];
			break;
		case TYPE_OFFLINE_ERRZIP:
			NSLog(@"OfflineDemao error zip files num: %d", state);
			break;
		case TYPE_OFFLINE_UNZIPFINISH:
			//NSLog(@"OfflineDemao scan finish");
			_label.text = [NSString stringWithFormat:@"OfflineDemao scan finish:%d", state];
			[_alertView dismissWithClickedButtonIndex:0 animated:NO];
			[_alertView release];
			_alertView = nil;
			break;
		case TYPE_OFFLINE_ADD:
			NSLog(@"OfflineDemo add num:%d", state);
			_label.text = [NSString stringWithFormat:@"OfflineDemo add num:%d", state];
			break;
		case TYPE_OFFLINE_NEWVER:
			NSLog(@"OfflineDemo new offlinemap ver");
			break;
		default:
			break;
	}
}

- (BOOL)textFieldShouldReturn:(UITextField *)textField
{
	[textField resignFirstResponder];
	return YES;
}


@end
