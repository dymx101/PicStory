//
//  AnnotationDemoViewController.m
//  BaiduMapApiDemo
//
//  Copyright 2011 Baidu Inc. All rights reserved.
//

#import "AnnotationDemoViewController.h"


@implementation AnnotationDemoViewController

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
	
	// 添加一个PointAnnotation
	BMKPointAnnotation* annotation = [[BMKPointAnnotation alloc]init];
	CLLocationCoordinate2D coor;
	coor.latitude = 39.915;
	coor.longitude = 116.404;
	annotation.coordinate = coor;
	
	annotation.title = @"test";
	annotation.subtitle = @"this is a test!";
	[mapView addAnnotation:annotation];
}

- (void)viewWillAppear:(BOOL)animated
{
    // 设置mapView的Delegate
	mapView.delegate = self;
}

// Override
- (BMKAnnotationView *)mapView:(BMKMapView *)mapView viewForAnnotation:(id <BMKAnnotation>)annotation
{
	if ([annotation isKindOfClass:[BMKPointAnnotation class]]) {
		BMKPinAnnotationView *newAnnotation = [[BMKPinAnnotationView alloc] initWithAnnotation:annotation reuseIdentifier:@"myAnnotation"];   
		newAnnotation.pinColor = BMKPinAnnotationColorPurple;   
		newAnnotation.animatesDrop = YES;
		newAnnotation.draggable = YES;
		
		return newAnnotation;   
	}
	return nil;
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


@end
