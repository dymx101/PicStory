//
//  TMDViewController.m
//  CameraDemo
//
//  Created by dong yiming on 13-2-23.
//  Copyright (c) 2013年 yim. All rights reserved.
//

#import "TMDViewController.h"
#import <AssetsLibrary/AssetsLibrary.h>

@interface TMDViewController ()
@property (weak, nonatomic) IBOutlet UIImageView *imageView;

@end

@implementation TMDViewController
{
    ALAssetsLibrary *library;
}

-(void)viewDidAppear:(BOOL)animated
{
    //[self _showCameraToTakePhoto];
    //[self _showVideosAndPhotosFromLibrary];
    
    if (self.videoURLToEdit) {
        NSString *videoPath = self.videoURLToEdit.path;
        
        // first let's make sure the video editor is able to edit the video at the path in our documents folder
        if ([UIVideoEditorController canEditVideoAtPath:videoPath]) {
            // instantiate the video editor
            UIVideoEditorController *videoEditor = [[UIVideoEditorController alloc] init];
            // make sure to set the path of the video
            videoEditor.videoPath = videoPath;
            // and present the video editor
            [self presentModalViewController:videoEditor animated:YES];
            self.videoURLToEdit = nil;
        }
        else
        {
            NSLog(@"cannot edit the video at this path");
        }
    }
}

- (void)viewDidLoad
{
    [super viewDidLoad];
    
    library = [[ALAssetsLibrary alloc] init];
    
    //[self _saveVideoToAlbum];
    //[self _checkLog];
    
}

- (void)viewDidUnload {
    [self setImageView:nil];
    [super viewDidUnload];
}


#pragma mark -
-(void)_checkLog
{
    if ([self isFrontCameraAvailable] == YES)
    {
        NSLog(@"The front camera is available.");
        
        if ([self isFlashAvailableOnFrontCamera])
        {
            NSLog(@"The front camera is equipped with a flash");
        }
        else
        {
            NSLog(@"The front camera is not equipped with a flash");
        }
    }
    else
    {
        NSLog(@"The front camera is not available.");
    }
    
    if ([self isRearCameraAvailable] == YES)
    {
        NSLog(@"The rear camera is available.");
        if ([self isFlashAvailableOnRearCamera] == YES)
        {
            NSLog(@"The rear camera is equipped with a flash");
        }
        else
        {
            NSLog(@"The rear camera is not equipped with a flash");
        }
    }
    else
    {
        NSLog(@"The rear camera is not available.");
    }
	
    if ([self doesCameraSupportTakingPhotos])
    {
        NSLog(@"the camera support taking photo");
    }
    else
    {
        NSLog(@"the camera does not support taking photos");
    }
    
    if ([self doesCameraSupportShootingVideos])
    {
        NSLog(@"the camera support shooting videos");
    }
    else
    {
        NSLog(@"the camera does not support shooting videos");
    }
    
}

-(BOOL)isCameraAvailable
{
    return ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypeCamera]);
}

-(BOOL)isPhotoLibraryAvailable
{
    return ([UIImagePickerController isSourceTypeAvailable:UIImagePickerControllerSourceTypePhotoLibrary]);
}

-(BOOL)canUserPickVideosFromPhotoLibrary
{
    BOOL result = NO;
    result = [self doesCameraSupportMediaType:(NSString *)kUTTypeMovie onSourceType:UIImagePickerControllerSourceTypePhotoLibrary];
    return result;
}

-(BOOL)canUserPickPhotosFromPhotoLibrary
{
    BOOL result = NO;
    result = [self doesCameraSupportMediaType:(NSString *)kUTTypeImage onSourceType:UIImagePickerControllerSourceTypePhotoLibrary];
    return result;
}

-(BOOL)doesCameraSupportMediaType:(NSString *)paramMediaType onSourceType:(UIImagePickerControllerSourceType)paramSourceType
{
    BOOL result = NO;
    
    if (paramMediaType == nil || [paramMediaType length] == 0) {
        return (NO);
    }
    
    if (![UIImagePickerController isSourceTypeAvailable:paramSourceType]) {
        return (NO);
    }
    
    NSArray *mediaTypes = [UIImagePickerController availableMediaTypesForSourceType:paramSourceType];
    
    if (mediaTypes == nil) {
        return (NO);
    }
    
    for (NSString *mediaType in mediaTypes) {
        if ([mediaType isEqualToString:paramMediaType])
        {
            return (YES);
        }
    }
    
    return result;
}

// another way to check if camera support media of a source type
-(BOOL)cameraSupportsMedia:(NSString *)paramMediaType sourceType:(UIImagePickerControllerSourceType)paramSourceType
{
    __block BOOL result = NO;
    if ([paramMediaType length] == 0) {
        NSLog(@"media type is empty.");
        return NO;
    }
    
    NSArray *availableMediaTypes = [UIImagePickerController availableMediaTypesForSourceType:paramSourceType];
    [availableMediaTypes enumerateObjectsUsingBlock:^(id obj, NSUInteger idx, BOOL *stop) {
        NSString *mediaType = (NSString *)obj;
        if ([mediaType isEqualToString:paramMediaType]) {
            result = YES;
            *stop = YES;
        }
    }];
    
    return result;
}

-(BOOL)doesCameraSupportShootingVideos
{
    BOOL result = NO;
    result = [self doesCameraSupportMediaType:(NSString *)kUTTypeMovie onSourceType:UIImagePickerControllerSourceTypeCamera];
    
    return result;
}

-(BOOL)doesCameraSupportTakingPhotos
{
    BOOL result = NO;
    result = [self doesCameraSupportMediaType:(NSString *)kUTTypeImage onSourceType:UIImagePickerControllerSourceTypeCamera];
    
    return result;
}

-(BOOL)isFrontCameraAvailable
{
    BOOL result = NO;
#ifdef __IPHONE_4_0
    #if (__IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_4_0)
    result = [UIImagePickerController isCameraDeviceAvailable:UIImagePickerControllerCameraDeviceFront];
    #endif
#endif
    
    return result;
}

-(BOOL)isRearCameraAvailable
{
    BOOL result = NO;
#ifdef __IPHONE_4_0
#if (__IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_4_0)
    result = [UIImagePickerController isCameraDeviceAvailable:UIImagePickerControllerCameraDeviceRear];
#endif
#endif
    
    return result;
}

-(BOOL)isFlashAvailableOnRearCamera
{
    BOOL result = NO;
#ifdef __IPHONE_4_0
#if (__IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_4_0)
    result = [UIImagePickerController isFlashAvailableForCameraDevice:UIImagePickerControllerCameraDeviceRear];
#endif
#endif
    
    return result;
}

-(BOOL)isFlashAvailableOnFrontCamera
{
    BOOL result = NO;
#ifdef __IPHONE_4_0
#if (__IPHONE_OS_VERSION_MAX_ALLOWED >= __IPHONE_4_0)
    result = [UIImagePickerController isFlashAvailableForCameraDevice:UIImagePickerControllerCameraDeviceFront];
#endif
#endif
    
    return result;
}



// 从资源库中加载视频
-(void)_retrieveVideoFromLibrary
{
    dispatch_queue_t dispatchQueue = dispatch_get_global_queue(0, 0);
    dispatch_async(dispatchQueue, ^{
        
        [library enumerateGroupsWithTypes:ALAssetsGroupAll usingBlock:^(ALAssetsGroup *group, BOOL *stop) {
            
            __block BOOL foundTheVideo = NO;
            [group enumerateAssetsUsingBlock:^(ALAsset *result, NSUInteger index, BOOL *stop) {
                NSString *assetType = [result valueForProperty:ALAssetPropertyType];
                if ([assetType isEqualToString:ALAssetTypeVideo]) {
                    NSLog(@"this is a video asset");
                    
                    foundTheVideo = YES;
                    *stop = YES;
                    
                    // get the asset's representation object
                    ALAssetRepresentation *assetRepresentation = [result defaultRepresentation];
                    
                    const NSUInteger BufferSize = 1024;
                    uint8_t buffer[BufferSize];
                    NSUInteger bytesRead = 0;
                    long long currentOffset = 0;
                    NSError *readingError = nil;
                    
                    // find the documents folder
                    NSArray *documents = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
                    NSString *documentsFolder = documents[0];
                    NSString *videoPath = [documentsFolder stringByAppendingPathComponent:@"Temp.MOV"];
                    
                    NSFileManager *fileManager = [[NSFileManager alloc] init];
                    // create the file if it doesn't exsist already
                    if (![fileManager fileExistsAtPath:videoPath]) {
                        [fileManager createFileAtPath:videoPath contents:nil attributes:nil];
                    }
                    // we will use this file handle to write the contents of the media assets to disk
                    NSFileHandle *fileHandle = [NSFileHandle fileHandleForWritingAtPath:videoPath];
                    do {
                        // read as many bytes as we can put in the buffer
                        bytesRead = [assetRepresentation getBytes:(uint8_t *)&buffer fromOffset:currentOffset length:BufferSize error:&readingError];
                        // if we couldn't read anything, we will exit this loop
                        if (bytesRead == 0) {
                            break;
                        }
                        // keep the offset up to date
                        currentOffset += bytesRead;
                        // put the buffer into an NSData
                        NSData *readData = [[NSData alloc] initWithBytes:(const void *)buffer length:bytesRead];
                        // and write the data to file
                        [fileHandle writeData:readData];
                        
                    } while (bytesRead > 0);
                    
                    NSLog(@"finished reading and storing the video in the documents folder");
                }
            }];
            
            if (foundTheVideo) {
                *stop = YES;
            }
            
        } failureBlock:^(NSError *error) {
            NSLog(@"failed to enumerate the asset groups.");
        }];
        
    });
}

// 从资源库中加载图片
-(void)_retrievePhotoFromLibrary
{
    dispatch_queue_t dispatchQueue = dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_DEFAULT, 0);
    dispatch_async(dispatchQueue, ^{
        
        [library enumerateGroupsWithTypes:ALAssetsGroupAll usingBlock:^(ALAssetsGroup *group, BOOL *stop) {
            
            [group enumerateAssetsUsingBlock:^(ALAsset *result, NSUInteger index, BOOL *stop) {
                __block BOOL foundThePhoto = NO;
                if (foundThePhoto) {
                    *stop = YES;
                }
                
                // get the asset type
                NSString *assetType = [result valueForProperty:ALAssetPropertyType];
                
                if ([assetType isEqualToString:ALAssetTypePhoto]) {
                    NSLog(@"This is a photo asset");
                    
                    foundThePhoto = YES;
                    *stop = YES;
                    
                    // get the asset's representation object
                    ALAssetRepresentation *assetRepresentation = [result defaultRepresentation];
                    
                    // we need the scale and orientation to be able to construct a properly oriented and scaled UIImage out of the representation object
                    CGFloat imageScale = assetRepresentation.scale;
                    UIImageOrientation imageOrientation = (UIImageOrientation)assetRepresentation.orientation;
                    
                    dispatch_async(dispatch_get_main_queue(), ^{
                        CGImageRef imageReference = assetRepresentation.fullResolutionImage;
                        
                        // construct the image now
                        UIImage *image = [[UIImage alloc] initWithCGImage:imageReference scale:imageScale orientation:imageOrientation];
                        
                        if (image) {
                            self.imageView.contentMode = UIViewContentModeScaleAspectFit;
                            self.imageView.image = image;
                        }
                        else
                        {
                            NSLog(@"failed to create the image");
                        }
                    });
                }
            }];
            
        } failureBlock:^(NSError *error) {
            NSLog(@"failed to enumerate the asset groups.");
        }];
        
    });
}

// 遍历资源库
-(void)_scanDataFromLibrary
{
    
    [library enumerateGroupsWithTypes:ALAssetsGroupAll usingBlock:^(ALAssetsGroup *group, BOOL *stop) {
        
        [group enumerateAssetsUsingBlock:^(ALAsset *result, NSUInteger index, BOOL *stop) {
            
            // get the asset type
            NSString *assetType = [result valueForProperty:ALAssetPropertyType];
            
            if ([assetType isEqualToString:ALAssetTypePhoto]) {
                NSLog(@"This is a photo asset");
            }
            
            else if ([assetType isEqualToString:ALAssetTypeVideo])
            {
                NSLog(@"This is a video asset");
            }
            
            else if ([assetType isEqualToString:ALAssetTypeUnknown])
            {
                NSLog(@"This is a unknown asset");
            }
            
            // get the URLs for the asset
            NSDictionary *assetURLs = [result valueForProperty:ALAssetPropertyURLs];
            
            NSUInteger assetCounter = 0;
            for (NSString *assetURLKey in assetURLs) {
                assetCounter++;
                NSLog(@"Asset URL %lu = %@", (unsigned long)assetCounter, [assetURLs valueForKey:assetURLKey]);
            }
            
            // get the asset's representation object
            ALAssetRepresentation *assetRepresentaion = [result defaultRepresentation];
            
            NSLog(@"Representation Size = %lld", [assetRepresentaion size]);
            
        }];
        
        
        
    } failureBlock:^(NSError *error){
        NSLog(@"Failed to enumerate the asset groups.");
    } ];
    
}

-(void)_saveVideoToAlbum
{
    NSBundle *mainBundle = [NSBundle mainBundle];
    NSURL *videoFileURL = [mainBundle URLForResource:@"MyVideo" withExtension:@"MOV"];
    
    void(^handleVideoWasSuccessfully)(NSURL *, NSError *) = ^(NSURL *assetURL, NSError *error){
        if (error == nil)
        {
            NSLog(@"no error happened");
        }
        else
        {
            NSLog(@"Error happened while saving the video, the error is = %@", error);
        }
    };
    
    if (videoFileURL) {
        [library writeVideoAtPathToSavedPhotosAlbum:videoFileURL completionBlock:handleVideoWasSuccessfully];
    }
    else
    {
        NSLog(@"could not find the MyVideo.MOV file in the app bundle");
    }
}



-(void)_pickVideoFromLibrary
{
    if ([self isPhotoLibraryAvailable] && [self canUserPickVideosFromPhotoLibrary]) {
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        
        // set the source type to photo library
        imagePicker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
        // and we want our user to be able to pick movies from the library
        NSArray *mediaTypes = [[NSArray alloc] initWithObjects:(NSString *)kUTTypeMovie, nil];
        imagePicker.mediaTypes = mediaTypes;
        // set the delegate to the current view controller
        imagePicker.delegate = self;
        // present our image picker
        [self presentModalViewController:imagePicker animated:YES];
    }
}

-(void)_showVideosAndPhotosFromLibrary
{
    if ([self isPhotoLibraryAvailable]) {
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.sourceType = UIImagePickerControllerSourceTypePhotoLibrary;
        NSMutableArray *mediaTypes = [[NSMutableArray alloc] init];
        
        if ([self canUserPickPhotosFromPhotoLibrary]) {
            [mediaTypes addObject:(NSString *)kUTTypeImage];
        }
        
        if ([self canUserPickVideosFromPhotoLibrary]) {
            [mediaTypes addObject:(NSString *)kUTTypeMovie];
        }
        
        imagePicker.mediaTypes = mediaTypes;
        //imagePicker.mediaTypes = [UIImagePickerController availableMediaTypesForSourceType:imagePicker.sourceType];
        imagePicker.delegate = self;
        
        [self presentModalViewController:imagePicker animated:YES];
    }
    else
    {
        NSLog(@"the library is not availiable");
    }
}


-(void)_showCameraToTakePhoto
{
    if ([self isCameraAvailable] && [self doesCameraSupportTakingPhotos]) {
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.sourceType = UIImagePickerControllerSourceTypeCamera;
        imagePicker.mediaTypes = [UIImagePickerController availableMediaTypesForSourceType:imagePicker.sourceType];//[NSArray arrayWithObject:(NSString *)kUTTypeImage];
        imagePicker.allowsEditing = YES;
        imagePicker.delegate = self;
        
        [self presentModalViewController:imagePicker animated:YES];
    }
    else
    {
        NSLog(@"the camera is not availiable");
    }
}

-(void)_showCameraToShootVideo
{
    if ([self isCameraAvailable] && [self doesCameraSupportShootingVideos]) {
        UIImagePickerController *imagePicker = [[UIImagePickerController alloc] init];
        imagePicker.sourceType = UIImagePickerControllerSourceTypeCamera;
        imagePicker.mediaTypes = [NSArray arrayWithObject:(NSString *)kUTTypeMovie];
        imagePicker.allowsEditing = YES;
        imagePicker.delegate = self;
        
        imagePicker.videoQuality = UIImagePickerControllerQualityTypeHigh;
        imagePicker.videoMaximumDuration = 3.f;
        
        [self presentModalViewController:imagePicker animated:YES];
    }
    else
    {
        NSLog(@"the camera is not available");
    }
}

#pragma mark - delegate
-(void)imagePickerController:(UIImagePickerController *)picker didFinishPickingMediaWithInfo:(NSDictionary *)info
{
    NSLog(@"Picker returned successfully");
    
    NSString *mediaType = [info objectForKey:UIImagePickerControllerMediaType];
    
    if ([mediaType isEqualToString:(NSString *)kUTTypeMovie]) {
        NSURL *urlOfVideo = [info objectForKey:UIImagePickerControllerMediaURL];
        NSLog(@"Video URL = %@", urlOfVideo);
        self.videoURLToEdit = urlOfVideo;
        
        NSError *dataReadingError = nil;
        NSData *videoData = [NSData dataWithContentsOfURL:urlOfVideo options:NSDataReadingMapped error:&dataReadingError];
        if (videoData) {
            NSLog(@"successfully loaded the data");
        }
        else
        {
            NSLog(@"failed to load the data with error = %@", dataReadingError);
        }
    }
    else if ([mediaType isEqualToString:(NSString *)kUTTypeImage])
    {
        // metadata is only for images.
        //NSDictionary *metadata = [info objectForKey:UIImagePickerControllerMediaMetadata];
        UIImage *theImage;
        if (picker.allowsEditing) {
            theImage = [info objectForKey:UIImagePickerControllerEditedImage];
        } else {
            theImage = [info objectForKey:UIImagePickerControllerOriginalImage];
        }
        
        NSString *targetSelectorAsString = @"imageWasSavedSuccessfully:didFinishSavingWithError:contextInfo:";
        SEL targetSelector = NSSelectorFromString(targetSelectorAsString);
        NSLog(@"saving the photo ...");
        UIImageWriteToSavedPhotosAlbum(theImage, self, targetSelector, nil);
        
//        NSLog(@"Image metadata = %@", metadata);
//        NSLog(@"Image = %@", theImage);
    }
    
    [picker dismissModalViewControllerAnimated:YES];
}

-(void)imagePickerControllerDidCancel:(UIImagePickerController *)picker
{
    NSLog(@"Picker was Cancelled");
    self.videoURLToEdit = nil;
    [picker dismissModalViewControllerAnimated:YES];
}

-(void)imageWasSavedSuccessfully:(UIImage *)paramImage didFinishSavingWithError:(NSError *)paramError contextInfo:(void *)paramContextInfo
{
    if (paramError == nil) {
        NSLog(@"Image was saved successfully");
    }
    else
    {
        NSLog(@"an error happened while saving the image. \n Error = %@", paramError);
    }
}



#pragma mark - video editor controller delegate
-(void)videoEditorController:(UIVideoEditorController *)editor didSaveEditedVideoToPath:(NSString *)editedVideoPath
{
    NSLog(@"the video editor finished saving video");
    NSLog(@"the edited video path is at = %@", editedVideoPath);
    [editor dismissModalViewControllerAnimated:YES];
}

-(void)videoEditorController:(UIVideoEditorController *)editor didFailWithError:(NSError *)error
{
    NSLog(@"video editor error occoured = %@", error);
    [editor dismissModalViewControllerAnimated:YES];
}

-(void)videoEditorControllerDidCancel:(UIVideoEditorController *)editor
{
    NSLog(@"the video editor was cancelled");
    [editor dismissModalViewControllerAnimated:YES];
}
@end
