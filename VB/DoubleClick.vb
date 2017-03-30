Private Sub Worksheet_BeforeDoubleClick(ByVal Target As Range, Cancel As Boolean)

'Double click to add or remove name to cell
'Added condition code to only input names in thin boardered cells
'Check for empty comment field, exit if true
    If Cells(Rows.count, "G").End(xlUp) = Range("F3").Value And Cells(Rows.count, "G").End(xlUp).Offset(0, 1) = "" Then
        MsgBox "Please comment empty Extra Files field(s)."
        Exit Sub
    End If
    If Not Intersect(Target, Range("D:D")) Is Nothing Then
        Cancel = True
        If ActiveCell.Value = "" And ActiveCell.Borders(xlEdgeRight).LineStyle = xlContinuous And ActiveCell.Borders(xlEdgeLeft).Weight = xlThin Then
            ActiveCell.Value = Sheet02.Range("F3").Value
            If ActiveCell.Interior.ColorIndex = 15 Then
                ActiveCell.Offset(ColumnOffset:=-2).Interior.ColorIndex = 35
            End If
        ElseIf ActiveCell.Value = Range("F3").Value Then
            ActiveCell.Value = ""
            If ActiveCell.Interior.ColorIndex = 15 Then
                ActiveCell.Offset(ColumnOffset:=-2).Interior.ColorIndex = 2
            End If
            
        End If
    End If

End Sub

