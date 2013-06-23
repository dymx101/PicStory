//
//	 ______    ______    ______    
//	/\  __ \  /\  ___\  /\  ___\   
//	\ \  __<  \ \  __\_ \ \  __\_ 
//	 \ \_____\ \ \_____\ \ \_____\ 
//	  \/_____/  \/_____/  \/_____/ 
//
//	Copyright (c) 2012 BEE creators
//	http://www.whatsbug.com
//
//	Permission is hereby granted, free of charge, to any person obtaining a
//	copy of this software and associated documentation files (the "Software"),
//	to deal in the Software without restriction, including without limitation
//	the rights to use, copy, modify, merge, publish, distribute, sublicense,
//	and/or sell copies of the Software, and to permit persons to whom the
//	Software is furnished to do so, subject to the following conditions:
//
//	The above copyright notice and this permission notice shall be included in
//	all copies or substantial portions of the Software.
//
//	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//	IN THE SOFTWARE.
//
//
//  NSObject+BeeNotification.h
//

#pragma mark -

@interface NSNotification(BeeNotification)

- (BOOL)is:(NSString *)name;
- (BOOL)isKindOf:(NSString *)prefix;

@end

#pragma mark -

@interface NSObject(BeeNotification)

+ (NSString *)NOTIFICATION;

- (void)handleNotification:(NSNotification *)notification;  // 处理通知

- (void)observeNotification:(NSString *)name;               // 注册观察者
- (void)unobserveNotification:(NSString *)name;             // 反注册观察者
- (void)unobserveAllNotifications;                          // 反注册所有通知

- (BOOL)postNotification:(NSString *)name;                                  // 发送通知
- (BOOL)postNotification:(NSString *)name withObject:(NSObject *)object;    // 发送通知 with 对象

@end

#define GG_NOTIFY_GET_STARTED           @"GG_NOTIFY_GET_STARTED"
#define GG_NOTIFY_LOG_OUT               @"GG_NOTIFY_LOG_OUT"
#define GG_NOTIFY_LOG_IN                @"GG_NOTIFY_LOG_IN"
#define GG_NOTIFY_PROVINCE_CHANGED      @"GG_NOTIFY_PROVINCE_CHANGED"//省份改变
