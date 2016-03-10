$(document).ready(function ()
{
	var al = $(".alert");
	if (al)
	{
		setTimeout(function ()
		{
			$(al).hide(1000);
		}, 4000);
	}

	var IntCurrent = parseInt($(".current").find("span").text());
	var viewMore = $("#viewMore");
	viewMore.on("click", function (e)
	{
		e.preventDefault();
		e.stopPropagation();

		if ($(".pagination li").eq(IntCurrent).find("a").attr("href") == undefined)
		{
			viewMore.removeClass("btn-default").addClass("btn-warning");
			viewMore.find("span").removeClass("glyphicon-refresh").addClass("glyphicon-remove");

			return false;
		}

		IntCurrent++;
		var page = IntCurrent.toString();

		if (page.trim() != "" && page != null)
		{
			$.ajax({
				type: "POST",
				url: window.location.origin + "/search/rest",
				contentType: "application/json; charset=utf-8",
				dataType: "json",
				data: JSON.stringify({"pageNumber": page}),
				success: function (data)
				{
					getResult(data);
				},
				error: function ()
				{
					console.log("error");
					return false;
				}
			});
		}
	});

	$(".table").on("click", ".clickable", function (e)
	{
		e.preventDefault();
		e.stopPropagation();
		document.location.href = $(this).find("a").attr("href");
	});

	function getResult(data)
	{
		var pages = data.pages;
		var length = pages.length;
		var str = "";

		for (var i = 0; i < length; i++)
		{
			str = str + "<tr><td class='clickable'>" + pages[i] + "</td></tr>"
		}
		str = str.replace(/\[start\]/mig, "<span class='firefly'>")
			.replace(/\[end\]/mig, "</span>")
			.replace(/\[date\]/mig, "<span class='fireflyDate'>...</span>");

		$(".table tbody").append(str);
	}
});