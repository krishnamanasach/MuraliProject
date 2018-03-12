package com.objectrepos;

import org.openqa.selenium.support.PageFactory;

import com.base.BaseEngine;

public class TravellersPage extends BaseEngine {
static {
	PageFactory.initElements(getDriver(), TravellersPage.class);
}
}
