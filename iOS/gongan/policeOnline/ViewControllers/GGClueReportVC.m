//
//  GGClueReport.m
//  policeOnline
//
//  Created by Dong Yiming on 6/15/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGClueReportVC.h"

#import "GGAppDelegate.h"

@interface GGClueReportVC ()
@property (weak, nonatomic) IBOutlet UIImageView *ivCaptured;
@property (weak, nonatomic) IBOutlet UIButton *btnCaptured;
@property (weak, nonatomic) IBOutlet UIScrollView *viewScroll;


@end

@implementation GGClueReportVC
{
    UIImage     *_capturedImage;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        [self setMyTitle:@"线索征集"];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.navigationItem.leftBarButtonItem = nil;
    
   
    _btnCaptured.enabled = NO;
    
    _viewScroll.contentSize = CGSizeMake(_viewScroll.contentSize.width, CGRectGetMaxY(_ivCaptured.frame) + 20);
    
}

#pragma mark - scroll view delegate
-(void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}

-(IBAction)submit:(id)sender
{
    
}

-(IBAction)addPicture:(id)sender
{
    UIActionSheet *shit = [[UIActionSheet alloc] initWithTitle:@"添加照片" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"拍照", @"相册",nil];
    [shit showInView:GGSharedDelegate.tabBarController.view];
}

-(void)takePicture
{
    UIImagePickerController *controller = [[UIImagePickerController alloc] init];
    controller.sourceType = UIImagePickerControllerSourceTypeCamera;
    
    NSString *requiredMediaType = (__bridge NSString *)kUTTypeImage; controller.mediaTypes = [[NSArray alloc]
                                                                                              initWithObjects:requiredMediaType, nil]; controller.allowsEditing = YES;
    controller.delegate = self;
    [self.navigationController presentViewController:controller animated:YES completion:nil];
}

-(void)visitAlbum
{
    UIImagePickerController *controller = [[UIImagePickerController alloc] init];
    controller.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
    
    NSMutableArray *mediaTypes = [[NSMutableArray alloc] init];
    [mediaTypes addObject:(__bridge NSString *)kUTTypeImage];
    
    controller.mediaTypes = mediaTypes;
    controller.delegate = self;
    [self.navigationController presentModalViewController:controller animated:YES];
}

#pragma mark - action sheet
- (void)actionSheet:(UIActionSheet *)actionSheet clickedButtonAtIndex:(NSInteger)buttonIndex
{
    if (buttonIndex == 0)
    {
        [self takePicture];
    }
    else if (buttonIndex == 1)
    {
        [self visitAlbum];
    }
}

#pragma mark - camera delegate
- (void) imagePickerController:(UIImagePickerController *)picker
 didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    NSLog(@"Picker returned successfully.");
    NSString *mediaType = [info objectForKey:UIImagePickerControllerMediaType];
    
    if ([mediaType isEqualToString:(__bridge NSString *)kUTTypeMovie])
    {
        NSURL *urlOfVideo = [info objectForKey:UIImagePickerControllerMediaURL];
        NSLog(@"Video URL = %@", urlOfVideo);
    }
    else if ([mediaType isEqualToString:(__bridge NSString *)kUTTypeImage])
    { /* Let's get the metadata. This is only for images. Not videos */
        NSDictionary *metadata = [info objectForKey:UIImagePickerControllerMediaMetadata];
        
        _capturedImage = [info objectForKey:UIImagePickerControllerOriginalImage];
        NSLog(@"Image Metadata = %@", metadata);
        NSLog(@"Image = %@", _capturedImage);
        
        _ivCaptured.image = _capturedImage;
        _btnCaptured.enabled = YES;
    }
    
    [picker dismissModalViewControllerAnimated:YES];
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    NSLog(@"Picker was cancelled");
    [picker dismissModalViewControllerAnimated:YES];
}

- (void)viewDidUnload {
    [self setIvCaptured:nil];
    [self setIvCaptured:nil];
    [self setBtnCaptured:nil];
    [self setViewScroll:nil];
    [super viewDidUnload];
}
@end
