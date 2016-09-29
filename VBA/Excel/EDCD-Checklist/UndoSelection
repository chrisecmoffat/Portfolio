Sub NoSpace_Color()

'Removes selected coloured cell and trims excess spaces
    Dim c As Range
    For Each c In Selection.Cells
        c = Trim(c)
        If c.Interior.ColorIndex <> 15 Then
            c.Interior.ColorIndex = 2
        End If
        If c = Range("F3").Value Then
            c = ""
        End If
    Next
    
End Sub
