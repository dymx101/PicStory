//
//  GGClueReport.m
//  policeOnline
//
//  Created by Dong Yiming on 6/15/13.
//  Copyright (c) 2013 tmd. All rights reserved.
//

#import "GGClueReportVC.h"

#import "GGAppDelegate.h"
#import "UIDevice+IdentifierAddition.h"

@interface GGClueReportVC ()
//@property (weak, nonatomic) IBOutlet UIImageView *ivCaptured;
//@property (weak, nonatomic) IBOutlet UIButton *btnCaptured;
@property (weak, nonatomic) IBOutlet UIScrollView *viewScroll;
@property (weak, nonatomic) IBOutlet UIButton *btnSubmit;

@property (weak, nonatomic) IBOutlet UIButton *btnCaptured1;
@property (weak, nonatomic) IBOutlet UIButton *btnCaptured2;
@property (weak, nonatomic) IBOutlet UIButton *btnCaptured3;
@property (weak, nonatomic) IBOutlet UIButton *btnCaptured4;
@property (weak, nonatomic) IBOutlet UIButton *btnCaptured5;

@property (weak, nonatomic) IBOutlet UITextView *viewText;

@end

@implementation GGClueReportVC
{
    //UIImage     *_capturedImage;
    NSMutableArray          *_cachedImages;
    NSArray                     *_capturedButtons;
    UIImage                 *_defaultBtnImg;
    int                     _indexForDelete;
}

- (id)initWithNibName:(NSString *)nibNameOrNil bundle:(NSBundle *)nibBundleOrNil
{
    self = [super initWithNibName:nibNameOrNil bundle:nibBundleOrNil];
    if (self) {
        [self setMyTitle:@"线索征集"];
        _cachedImages = [NSMutableArray arrayWithCapacity:5];
        //_capturedButtons = [NSMutableArray arrayWithCapacity:5];
        _defaultBtnImg = [UIImage imageNamed:@"btnAddPic"];
    }
    return self;
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    self.navigationItem.leftBarButtonItem = nil;
    _capturedButtons = @[_btnCaptured1, _btnCaptured2, _btnCaptured3, _btnCaptured4, _btnCaptured5];
    [self _updateCapturedButtons];
    //_btnCaptured.enabled = NO;
    
    _viewScroll.contentSize = CGSizeMake(_viewScroll.contentSize.width, CGRectGetMaxY(_btnSubmit.frame) + 20);
    
}

-(void)_updateCapturedButtons
{
    int btnCount = _capturedButtons.count;
    int imageCount = _cachedImages.count;
    for (int i = 0; i < btnCount; i++)
    {
        UIButton *btn = _capturedButtons[i];
        btn.layer.borderWidth = 1;
        btn.layer.borderColor = GGSharedColor.white.CGColor;
        if (i < imageCount)
        {
            btn.hidden = NO;
            id dic = _cachedImages[i];
            UIImage *image = [dic objectForKey:@"image"];
            [btn setImage:image forState:UIControlStateNormal];
        }
        else if (i == imageCount)
        {
            btn.hidden = NO;
            [btn setImage:_defaultBtnImg forState:UIControlStateNormal];
        }
        else
        {
            btn.hidden = YES;
        }
    }
}

#pragma mark - scroll view delegate
-(void)scrollViewDidScroll:(UIScrollView *)scrollView
{
    [self.view endEditing:YES];
}

-(IBAction)submit:(id)sender
{
#warning DUMMY CODE
    if (_viewText.text.length)
    {
        [GGSharedAPI reportClueWithContentID:1000 clueText:_viewText.text phoneID:[UIDevice macaddress] phone:[GGUserDefault myPhone] images:_cachedImages callback:^(id operation, id aResultObject, NSError *anError) {
            
            //GGApiParser *parser = [GGApiParser parserWithApiData:aResultObject];
            
            DLog(@"%@", aResultObject);
            
        }];
    }
    else
    {
        [GGAlert alert:@"请填写线索内容"];
        [_viewText becomeFirstResponder];
    }
}

-(IBAction)addPicture:(id)sender
{
    UIButton *btn = sender;
    if (btn.tag < _cachedImages.count)
    {
        // has captured
        _indexForDelete = btn.tag;
        UIActionSheet *shit = [[UIActionSheet alloc] initWithTitle:@"删除照片" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"删除", nil];
        shit.tag = 1000;
        [shit showInView:self.view.window];
    }
    else
    {
        UIActionSheet *shit = [[UIActionSheet alloc] initWithTitle:@"添加照片" delegate:self cancelButtonTitle:@"取消" destructiveButtonTitle:nil otherButtonTitles:@"拍照", @"相册",nil];
        shit.tag = 1001;
        [shit showInView:self.view.window];
    }
    
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
    if (actionSheet.tag == 1000)    // delete
    {
        if (buttonIndex == 0)
        {
            [self deletePicture];
        }
    }
    else if (actionSheet.tag == 1001) // add
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
}

-(void)deletePicture
{
    [_cachedImages removeObjectAtIndex:_indexForDelete];
    [self _updateCapturedButtons];
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
        
        UIImage *capturedImage = [info objectForKey:UIImagePickerControllerOriginalImage];
        NSLog(@"Image Metadata = %@", metadata);
        NSLog(@"Image = %@", capturedImage);
        
        //_ivCaptured.image = capturedImage;
        [self _cacheCapturedImage:capturedImage];
        
        //_btnCaptured.enabled = YES;
    }
    
    [picker dismissModalViewControllerAnimated:YES];
}

-(void)_cacheCapturedImage:(UIImage *)aCapturedImage
{
    if (aCapturedImage)
    {
        NSData *imageData = [NSData dataWithData:UIImagePNGRepresentation(aCapturedImage)];
        NSDate *date = [NSDate date];
        NSDateFormatter *fmtr = [[NSDateFormatter alloc] init];
        fmtr.dateFormat = @"yyyyMMddhhmmss";
        
        NSString *dateStr = [fmtr stringFromDate:date];
        NSString *imageName = [NSString stringWithFormat:@"%@.jpg", dateStr];
        DLog(@"%@", imageName);
        
        NSDictionary *imageDic = [NSDictionary dictionaryWithObjectsAndKeys:
                                  dateStr, @"name"
                                  , imageName, @"fileName"
                                  , imageData, @"data"
                                  , aCapturedImage, @"image", nil];
        [_cachedImages addObject:imageDic];
        
        [self _updateCapturedButtons];
    }
}

- (void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    NSLog(@"Picker was cancelled");
    [picker dismissModalViewControllerAnimated:YES];
}

- (void)viewDidUnload {

    [self setViewScroll:nil];
    [self setBtnCaptured1:nil];
    [self setBtnCaptured2:nil];
    [self setBtnCaptured3:nil];
    [self setBtnCaptured4:nil];
    [self setBtnCaptured5:nil];
    [self setBtnSubmit:nil];
    [self setViewText:nil];
    [super viewDidUnload];
}
@end
