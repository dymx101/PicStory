//
//  TestViewController.m
//  BaiduMapApiDemoSrc
//
//  Created by 孙振兴 on 11-10-13.
//  Copyright 2011 __MyCompanyName__. All rights reserved.
//

#import "TestViewController.h"


@implementation TestViewController

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

	_annotation = [[BMKPointAnnotation alloc]init];
	CLLocationCoordinate2D coor;
	coor.latitude = 39.915;
	coor.longitude = 115.404;
	_annotation.coordinate = coor;
	
	_annotation.title = @"这里是北京";
	//_annotation.subtitle = @"this is a test!";
	_annotation.subtitle = NULL;
	_text.text = @"test1";
	[mapView addAnnotation:_annotation];
}

- (void)viewWillAppear:(BOOL)animated
{
    // 设置mapView的Delegate
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
}

-(IBAction)onClickOK
{
	//_annotation.title = _text.text;
	//[mapView setNeedsDisplay];
	[mapView setCenterCoordinate:_annotation.coordinate animated:YES];
	_flag = NO;
	
}

- (void)mapView:(BMKMapView *)mmapView regionDidChangeAnimated:(BOOL)animated
{
	if (animated) {
		if (!_flag) {
			_flag = YES;
			[mmapView selectAnnotation:_annotation animated:YES];
		}
	}
	
}


@end
