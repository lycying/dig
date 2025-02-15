/******************************************************************************
 * Spine Runtime Software License - Version 1.1
 * 
 * Copyright (c) 2013, Esoteric Software
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms in whole or in part, with
 * or without modification, are permitted provided that the following conditions
 * are met:
 * 
 * 1. A Spine Essential, Professional, Enterprise, or Education License must
 *    be purchased from Esoteric Software and the license must remain valid:
 *    http://esotericsoftware.com/
 * 2. Redistributions of source code must retain this license, which is the
 *    above copyright notice, this declaration of conditions and the following
 *    disclaimer.
 * 3. Redistributions in binary form must reproduce this license, which is the
 *    above copyright notice, this declaration of conditions and the following
 *    disclaimer, in the documentation and/or other materials provided with the
 *    distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *****************************************************************************/

package com.esotericsoftware.spine.attachments;

import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.Slot;

import static com.badlogic.gdx.graphics.g2d.SpriteBatch.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.NumberUtils;

/** Attachment that displays a texture region. */
public class RegionAttachment extends Attachment {
	static public final int BLX = 0;
	static public final int BLY = 1;
	static public final int ULX = 2;
	static public final int ULY = 3;
	static public final int URX = 4;
	static public final int URY = 5;
	static public final int BRX = 6;
	static public final int BRY = 7;

	private TextureRegion region;
	private float x, y, scaleX = 1, scaleY = 1, rotation, width, height;
	private final float[] vertices = new float[20];
	private final float[] offset = new float[8];

	public RegionAttachment (String name) {
		super(name);
	}

	public void updateOffset () {
		float width = getWidth();
		float height = getHeight();
		float localX2 = width / 2;
		float localY2 = height / 2;
		float localX = -localX2;
		float localY = -localY2;
		if (region instanceof AtlasRegion) {
			AtlasRegion region = (AtlasRegion)this.region;
			if (region.rotate) {
				localX += region.offsetX / region.originalWidth * width;
				localY += region.offsetY / region.originalHeight * height;
				localX2 -= (region.originalWidth - region.offsetX - region.packedHeight) / region.originalWidth * width;
				localY2 -= (region.originalHeight - region.offsetY - region.packedWidth) / region.originalHeight * height;
			} else {
				localX += region.offsetX / region.originalWidth * width;
				localY += region.offsetY / region.originalHeight * height;
				localX2 -= (region.originalWidth - region.offsetX - region.packedWidth) / region.originalWidth * width;
				localY2 -= (region.originalHeight - region.offsetY - region.packedHeight) / region.originalHeight * height;
			}
		}
		float scaleX = getScaleX();
		float scaleY = getScaleY();
		localX *= scaleX;
		localY *= scaleY;
		localX2 *= scaleX;
		localY2 *= scaleY;
		float rotation = getRotation();
		float cos = MathUtils.cosDeg(rotation);
		float sin = MathUtils.sinDeg(rotation);
		float x = getX();
		float y = getY();
		float localXCos = localX * cos + x;
		float localXSin = localX * sin;
		float localYCos = localY * cos + y;
		float localYSin = localY * sin;
		float localX2Cos = localX2 * cos + x;
		float localX2Sin = localX2 * sin;
		float localY2Cos = localY2 * cos + y;
		float localY2Sin = localY2 * sin;
		float[] offset = this.offset;
		offset[BLX] = localXCos - localYSin;
		offset[BLY] = localYCos + localXSin;
		offset[ULX] = localXCos - localY2Sin;
		offset[ULY] = localY2Cos + localXSin;
		offset[URX] = localX2Cos - localY2Sin;
		offset[URY] = localY2Cos + localX2Sin;
		offset[BRX] = localX2Cos - localYSin;
		offset[BRY] = localYCos + localX2Sin;
	}

	public void setRegion (TextureRegion region) {
		if (region == null) throw new IllegalArgumentException("region cannot be null.");
		this.region = region;
		float[] vertices = this.vertices;
		if (region instanceof AtlasRegion && ((AtlasRegion)region).rotate) {
			vertices[U3] = region.getU();
			vertices[V3] = region.getV2();
			vertices[U4] = region.getU();
			vertices[V4] = region.getV();
			vertices[U1] = region.getU2();
			vertices[V1] = region.getV();
			vertices[U2] = region.getU2();
			vertices[V2] = region.getV2();
		} else {
			vertices[U2] = region.getU();
			vertices[V2] = region.getV2();
			vertices[U3] = region.getU();
			vertices[V3] = region.getV();
			vertices[U4] = region.getU2();
			vertices[V4] = region.getV();
			vertices[U1] = region.getU2();
			vertices[V1] = region.getV2();
		}
		updateOffset();
	}

	public TextureRegion getRegion () {
		if (region == null) throw new IllegalStateException("Region has not been set: " + this);
		return region;
	}

	public void updateWorldVertices (Slot slot, boolean premultipliedAlpha) {
		Skeleton skeleton = slot.getSkeleton();
		Color skeletonColor = skeleton.getColor();
		Color slotColor = slot.getColor();
		float color;
		if (premultipliedAlpha) {
			float a = 255 * skeletonColor.a * slotColor.a;
			color = NumberUtils.intToFloatColor( //
				((int)a << 24) //
					| ((int)(a * skeletonColor.b * slotColor.b) << 16) //
					| ((int)(a * skeletonColor.g * slotColor.g) << 8) //
					| ((int)(a * skeletonColor.r * slotColor.r)));
		} else {
			color = NumberUtils.intToFloatColor( //
				((int)(255 * skeletonColor.a * slotColor.a) << 24) //
					| ((int)(255 * skeletonColor.b * slotColor.b) << 16) //
					| ((int)(255 * skeletonColor.g * slotColor.g) << 8) //
					| ((int)(255 * skeletonColor.r * slotColor.r)));
		}

		float[] vertices = this.vertices;
		float[] offset = this.offset;
		Bone bone = slot.getBone();
		float x = bone.getWorldX() + skeleton.getX();
		float y = bone.getWorldY() + skeleton.getY();
		float m00 = bone.getM00();
		float m01 = bone.getM01();
		float m10 = bone.getM10();
		float m11 = bone.getM11();
		float offsetX, offsetY;
		offsetX = offset[BRX];
		offsetY = offset[BRY];
		vertices[X1] = offsetX * m00 + offsetY * m01 + x; // br
		vertices[Y1] = offsetX * m10 + offsetY * m11 + y;
		vertices[C1] = color;

		offsetX = offset[BLX];
		offsetY = offset[BLY];
		vertices[X2] = offsetX * m00 + offsetY * m01 + x; // bl
		vertices[Y2] = offsetX * m10 + offsetY * m11 + y;
		vertices[C2] = color;

		offsetX = offset[ULX];
		offsetY = offset[ULY];
		vertices[X3] = offsetX * m00 + offsetY * m01 + x; // ul
		vertices[Y3] = offsetX * m10 + offsetY * m11 + y;
		vertices[C3] = color;

		offsetX = offset[URX];
		offsetY = offset[URY];
		vertices[X4] = offsetX * m00 + offsetY * m01 + x; // ur
		vertices[Y4] = offsetX * m10 + offsetY * m11 + y;
		vertices[C4] = color;
	}

	public float[] getWorldVertices () {
		return vertices;
	}

	public float[] getOffset () {
		return offset;
	}

	public float getX () {
		return x;
	}

	public void setX (float x) {
		this.x = x;
	}

	public float getY () {
		return y;
	}

	public void setY (float y) {
		this.y = y;
	}

	public float getScaleX () {
		return scaleX;
	}

	public void setScaleX (float scaleX) {
		this.scaleX = scaleX;
	}

	public float getScaleY () {
		return scaleY;
	}

	public void setScaleY (float scaleY) {
		this.scaleY = scaleY;
	}

	public float getRotation () {
		return rotation;
	}

	public void setRotation (float rotation) {
		this.rotation = rotation;
	}

	public float getWidth () {
		return width;
	}

	public void setWidth (float width) {
		this.width = width;
	}

	public float getHeight () {
		return height;
	}

	public void setHeight (float height) {
		this.height = height;
	}
}
