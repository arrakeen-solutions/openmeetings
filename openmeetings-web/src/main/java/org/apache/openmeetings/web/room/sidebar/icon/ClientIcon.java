/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License") +  you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.openmeetings.web.room.sidebar.icon;

import static org.apache.openmeetings.web.app.Application.getOnlineClient;
import static org.apache.openmeetings.web.pages.BasePage.ALIGN_LEFT;
import static org.apache.openmeetings.web.pages.BasePage.ALIGN_RIGHT;
import static org.apache.openmeetings.web.util.CallbackFunctionHelper.addOnClick;

import org.apache.openmeetings.db.entity.basic.Client;
import org.apache.openmeetings.web.pages.BasePage;
import org.apache.openmeetings.web.room.RoomPanel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.core.request.handler.IPartialPageRequestHandler;
import org.apache.wicket.markup.html.WebMarkupContainer;

public abstract class ClientIcon extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;
	protected static final String ICON_CLASS = "ui-icon ";
	protected static final String CLS_CLICKABLE = "clickable ";
	protected final RoomPanel room;
	protected final boolean self;
	protected final String uid;
	protected String mainCssClass;
	protected final StringBuilder cssClass = new StringBuilder(ICON_CLASS);

	public ClientIcon(String id, String uid, RoomPanel room) {
		super(id);
		this.room = room;
		this.uid = uid;
		self = room.getClient().getUid().equals(uid);
		setOutputMarkupId(true);
		setOutputMarkupPlaceholderTag(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		update(null);
	}

	protected abstract String getTitle();

	protected String getAlign() {
		return ((BasePage)getPage()).isRtl() ? ALIGN_RIGHT : ALIGN_LEFT;
	}

	protected abstract boolean isClickable();

	protected abstract String getScript();

	protected void internalUpdate() {
	}

	public final void update(IPartialPageRequestHandler handler) {
		cssClass.setLength(0);
		StringBuilder cls = new StringBuilder(ICON_CLASS);
		cls.append(getAlign()).append(mainCssClass);
		if (isClickable()) {
			//request/remove
			cls.append(CLS_CLICKABLE);
			add(addOnClick(getScript()));
		} else {
			add(addOnClick(""));
		}
		internalUpdate();
		add(AttributeModifier.replace("title", getTitle()));
		add(AttributeModifier.replace("class", cls.append(cssClass)));
		if (handler != null) {
			handler.add(this);
		}
	}

	protected Client getClient() {
		return getOnlineClient(uid);
	}
}
