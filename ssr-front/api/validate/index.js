/**
 * 验证是否是Email 格式字符串
 * @param strEmail
 * @returns {boolean}
 */
export function isEmail(strEmail) {
  return (strEmail.search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) !== -1)
}

