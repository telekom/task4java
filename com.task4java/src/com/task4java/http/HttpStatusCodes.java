/*
 *  Copyright (c) 2014 Andree Hagelstein, Maik Schulze, Deutsche Telekom AG. All Rights Reserved.
 *  
 *  Filename: HttpStatusCodes.java
 */
package com.task4java.http;

public class HttpStatusCodes {

	public static int
		Continue                       = 100,
		SwitchingProtocols             = 101,
		Processing                     = 102,
		                              
		OK                             = 200,
		Created                        = 201,
		Accepted                       = 202,
		NonAuthoritativeInformation    = 203,
		NoContent                      = 204,
		ResetContent                   = 205,
		PartialContent                 = 206,
		MultiStatus                    = 207,
		AlreadyReported                = 208,
		IMUsed                         = 226,
		                              
		MultipleChoices                = 300,
		MovedPermanently               = 301,
		Found                          = 302,
		SeeOther                       = 303,
		NotModified                    = 304,
		UseProxy                       = 305,
		Reserved                       = 306,
		TemporaryRedirect              = 307,
		PermanentRedirect              = 308,
		                              
		BadRequest                     = 400,
		Unauthorized                   = 401,
		PaymentRequired                = 402,
		Forbidden                      = 403,
		NotFound                       = 404,
		MethodNotAllowed               = 405,
		NotAcceptable                  = 406,
		ProxyAuthenticationRequired    = 407,
		RequestTimeout                 = 408,
		Conflict                       = 409,
		Gone                           = 410,
		LengthRequired                 = 411,
		PreconditionFailed             = 412,
		RequestEntityTooLarge          = 413,
		RequestURITooLong              = 414,
		UnsupportedMediaType           = 415,
		RequestedRangeNotSatisfiable   = 416,
		ExpectationFailed              = 417,
		UnprocessableEntity            = 422,
		Locked                         = 423,
		FailedDependency               = 424,
		ReservedForWebDAV              = 425,
		UpgradeRequired                = 426,
		Unassigned427                  = 427,
		PreconditionRequired           = 428,
		TooManyRequests                = 429,
		Unassigned430                  = 430,
		RequestHeaderFieldsTooLarge    = 431,
		                              
		InternalServerError            = 500,
		NotImplemented                 = 501,
		BadGateway                     = 502,
		ServiceUnavailable             = 503,
		GatewayTimeout                 = 504,
		HTTPVersionNotSupported        = 505,
		VariantAlsoNegotiates          = 506,
		InsufficientStorage            = 507,
		LoopDetected                   = 508,
		Unassigned509                  = 509,
		NotExtended                    = 510,
		NetworkAuthenticationRequired  = 511;

}
