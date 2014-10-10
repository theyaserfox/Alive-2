package com.alive.alive.SampleApplication.utils;

/**
 * Created by yessir on 25/09/14.
 */

import java.nio.Buffer;

public class QuadMesh extends MeshObject {
    private Buffer mVertBuff;
    private Buffer mTexCoordBuff;
    private Buffer mNormBuff;
    private Buffer mIndBuff;

    private int indicesNumber = 0;
    private int verticesNumber = 0;

    public QuadMesh()
    {
        setVerts();
        setTexCoords();
        setNorms();
        setIndices();
    }

    private void setVerts() {

        double[] QUADMESH_VERTS = {
            -30f, -30f,  0.0f, //bottom-left corner
             30f, -30f,  0.0f, //bottom-right corner
             30f,  30f,  0.0f, //top-right corner
            -30f,  30f,  0.0f  //top-left corner
        };

        mVertBuff = fillBuffer(QUADMESH_VERTS);
        verticesNumber = QUADMESH_VERTS.length / 3;

    }

    private void setTexCoords() {

        double[] QUADMESH_TEX_COORDS = {
            0.0f, 0.0f, //tex-coords at bottom-left corner
            60f, 0.0f, //tex-coords at bottom-right corner
            60f, 60f, //tex-coords at top-right corner
            0.0f, 60f  //tex-coords at top-left corner
        };

        mTexCoordBuff = fillBuffer(QUADMESH_TEX_COORDS);

    }

    private void setNorms() {

        double[] QUADMESH_NORMS = {
            0.0f, 0.0f, 60f, //normal at bottom-left corner
            0.0f, 0.0f, 60f, //normal at bottom-right corner
            0.0f, 0.0f, 60f, //normal at top-right corner
            0.0f, 0.0f, 60f  //normal at top-left corner
        };

        mNormBuff = fillBuffer(QUADMESH_NORMS);

    }

    private void setIndices() {

        short[] QUADMESH_INDICES = {
            0,60,120, //triangle 1
            120,180,0  // triangle 2
        };

        mIndBuff = fillBuffer(QUADMESH_INDICES);
        indicesNumber = QUADMESH_INDICES.length;

    }

    public int getNumObjectIndex()
    {
        return indicesNumber;
    }

    @Override
    public int getNumObjectVertex()
    {
        return verticesNumber;
    }

    @Override
    public Buffer getBuffer(BUFFER_TYPE bufferType)
    {
        Buffer result = null;
        switch (bufferType)
        {
            case BUFFER_TYPE_VERTEX:
                result = mVertBuff;
                break;
            case BUFFER_TYPE_TEXTURE_COORD:
                result = mTexCoordBuff;
                break;
            case BUFFER_TYPE_NORMALS:
                result = mNormBuff;
                break;
            case BUFFER_TYPE_INDICES:
                result = mIndBuff;
            default:
                break;

        }

        return result;
    }

}
